# Convenciones de Desarrollo - PlanDiario Docente

## Estrategia de Branching

### Ramas Principales
- `main`: código en producción (protegida)
- `develop`: integración continua, base para features

### Ramas de Trabajo

Prefijos obligatorios:

- `feature/`: nuevas funcionalidades
- `fix/`: corrección de bugs
- `docs/`: cambios solo en documentación
- `chore/`: tareas de mantenimiento (configs, deps)
- `refactor/`: mejoras de código sin cambio funcional
- `test/`: adición o mejora de tests

**Formato:** `<tipo>/<numero-issue>-descripcion-corta`

**Ejemplos:**
```
feature/12-crear-planificacion-diaria
fix/23-validacion-fecha-planificacion
docs/1-clean-architecture-baseline
chore/5-update-spring-boot-version
refactor/18-extract-meta-value-object
test/9-add-planificacion-use-case-tests
```

### Reglas de Trabajo

1. **Nunca trabajar directamente en `main` o `develop`**
2. **Cada Issue → crear branch → abrir PR**
3. Pull Requests siempre hacia `develop` (no `main`)
4. Require al menos 1 review antes de merge (cuando haya equipo)
5. Eliminar branches después del merge

## Conventional Commits

Usamos [Conventional Commits](https://www.conventionalcommits.org/) en **inglés**.

### Formato

```
<tipo>(<scope>): <descripción corta>

[cuerpo opcional]

[footer opcional]
```

### Tipos Permitidos

- `feat`: nueva funcionalidad
- `fix`: corrección de bug
- `docs`: cambios en documentación
- `style`: formato, espacios (no afecta código)
- `refactor`: refactorización sin cambio funcional
- `test`: agregar o modificar tests
- `chore`: tareas de build, deps, configs
- `perf`: mejora de performance

### Scope (Opcional pero Recomendado)

Módulo o capa afectada:
- `domain`, `application`, `infrastructure`, `interfaces`
- `planificacion`, `meta`, `actividad`, `area`
- `api`, `persistence`, `config`

### Ejemplos Aplicados al Proyecto

```bash
# 1. Nueva feature en dominio
feat(domain): add PlanificacionDiaria entity with business rules

# 2. Implementación de caso de uso
feat(application): implement CrearPlanificacionUseCase

# 3. Endpoint REST
feat(api): add POST /planificaciones endpoint

# 4. Corrección de validación
fix(domain): validate fecha cannot be more than 1 year in future

# 5. Documentación de arquitectura
docs(architecture): add clean architecture baseline

# 6. Actualización de dependencias
chore(deps): upgrade Spring Boot to 3.2.1
```

### Commits con Breaking Changes

```bash
feat(api)!: change planificacion endpoint to use ISO date format

BREAKING CHANGE: fecha field now expects ISO-8601 format (yyyy-MM-dd)
instead of dd/MM/yyyy
```

## Convenciones de Nombres

### Paquetes
- Todo en minúsculas
- Sin guiones bajos ni camelCase
- Siguiendo estructura de capas: `com.plandocente.<capa>.<subcapa>`

### Clases

#### Domain
- **Entidades**: sustantivo singular, PascalCase
  - `PlanificacionDiaria`, `Meta`, `Actividad`
- **Value Objects**: sustantivo descriptivo
  - `Fecha`, `DescripcionActividad`, `NombreArea`
- **Repositorios (interfaces)**: `<Entidad>Repository`
  - `PlanificacionRepository`, `MetaRepository`
- **Excepciones**: `<Concepto>Exception`
  - `FechaInvalidaException`, `MetaDuplicadaException`

#### Application
- **Use Cases**: verbo infinitivo + `UseCase`
  - `CrearPlanificacionUseCase`, `ActualizarMetaUseCase`
- **Services**: `<Concepto>Service`
  - `PlanificacionService`, `MetaService`
- **DTOs/Commands**: `<Verbo><Concepto>Command` o `<Concepto>Query`
  - `CrearPlanificacionCommand`, `ListarPlanificacionesQuery`

#### Infrastructure
- **JPA Entities**: `<Concepto>JpaEntity`
  - `PlanificacionJpaEntity`, `MetaJpaEntity`
- **Repository Impl**: `Jpa<Concepto>Repository`
  - `JpaPlanificacionRepository`
- **Adapters**: `<Concepto>RepositoryAdapter`
  - `PlanificacionRepositoryAdapter`

#### Interfaces
- **Controllers**: `<Concepto>Controller`
  - `PlanificacionController`, `MetaController`
- **Request DTOs**: `<Verbo><Concepto>RequestDto`
  - `CrearPlanificacionRequestDto`
- **Response DTOs**: `<Concepto>ResponseDto`
  - `PlanificacionResponseDto`, `MetaResponseDto`

### Endpoints REST

**Formato:** `/api/v1/<recurso-plural>`

**Ejemplos:**
```
GET    /api/v1/planificaciones
GET    /api/v1/planificaciones/{id}
POST   /api/v1/planificaciones
PUT    /api/v1/planificaciones/{id}
DELETE /api/v1/planificaciones/{id}

GET    /api/v1/planificaciones?fecha=2026-04-03
GET    /api/v1/metas
GET    /api/v1/metas/{id}/planificaciones
```

**Convenciones:**
- Recursos en plural
- Lowercase con guiones para separar palabras
- Versionado con `/v1`
- Query params para filtros
- Path params para IDs

### Variables y Métodos

- **camelCase** para variables y métodos
- Métodos de repositorio: `findBy...`, `save`, `delete`, `existsBy...`
- Use Cases: `execute(command)` o `handle(query)`
- Controllers: verbos HTTP estándar

**Ejemplos:**
```java
// Variables
LocalDate fechaPlanificacion;
List<Actividad> actividadesDelDia;

// Métodos
PlanificacionDiaria crearPlanificacion(CrearPlanificacionCommand command);
Optional<PlanificacionDiaria> findByFecha(LocalDate fecha);
```

## Estilo de Código

### Generales
- **Idioma del código**: español para conceptos de dominio, inglés para técnico
- **Indentación**: 4 espacios (no tabs)
- **Longitud de línea**: máximo 120 caracteres
- **Encoding**: UTF-8

### Java
- Seguir [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Imports: no usar wildcard (`*`), ordenar alfabéticamente
- Constantes en `UPPER_SNAKE_CASE`
- No abreviar nombres (excepto convenciones conocidas)

### Anotaciones
- Una anotación por línea
- `@Override` siempre explícito
- Spring annotations antes de las de validación

**Ejemplo:**
```java
@RestController
@RequestMapping("/api/v1/planificaciones")
@RequiredArgsConstructor
public class PlanificacionController {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanificacionResponseDto crear(
            @Valid @RequestBody CrearPlanificacionRequestDto request) {
        // ...
    }
}
```

## Testing

### Nomenclatura de Tests
- Clases: `<ClaseTesteada>Test` o `<ClaseTesteada>IntegrationTest`
- Métodos: `should<ComportamientoEsperado>_when<Condicion>`

**Ejemplos:**
```java
@Test
void shouldCreatePlanificacion_whenValidDataProvided() { }

@Test
void shouldThrowException_whenFechaIsInvalid() { }
```

### Organización
- Tests unitarios en mismo paquete que la clase (en `/test`)
- Tests de integración en paquete `integration`
- Usar Given-When-Then como estructura

## Documentación en Código

### JavaDoc
- **Obligatorio** para:
  - Clases públicas de Domain y Application
  - Métodos públicos de casos de uso
  - Interfaces de repositorio
- **Opcional** para:
  - Controllers (preferir OpenAPI/Swagger)
  - Métodos privados evidentes

**Ejemplo:**
```java
/**
 * Entidad raíz que representa la planificación pedagógica de un día específico.
 * Contiene las actividades, áreas y metas asociadas a una fecha concreta.
 */
public class PlanificacionDiaria {
    
    /**
     * Agrega una actividad a la planificación.
     *
     * @param actividad la actividad a agregar
     * @throws IllegalArgumentException si ya se alcanzó el máximo de actividades
     */
    public void agregarActividad(Actividad actividad) { }
}
```

## Configuración del IDE

### IntelliJ IDEA (Recomendado)
- Code Style: Google Java Style
- Save Actions: format on save, optimize imports
- Lombok Plugin habilitado
- SonarLint para análisis estático

## Alcance del MVP

**Fuera del MVP inicial:**
- Autenticación y autorización (sin usuarios en esta fase)
- OpenAPI/Swagger documentation
- Auditoría avanzada (createdBy, updatedBy)
- Soft deletes

El MVP se enfoca en el CRUD básico de planificaciones, metas, actividades y áreas sin gestión de usuarios.

## Notas para Agentes IA

- **Commits atómicos**: un cambio lógico por commit
- **PRs pequeños**: máximo 300-400 líneas modificadas
- Siempre crear Issue antes del PR
- Linkear PR con Issue usando "Closes #<numero>"
- Tests obligatorios para nueva lógica de negocio
- Revisar convenciones antes de generar código
- **No asumir requisitos no documentados**: el MVP no incluye usuarios ni autenticación

