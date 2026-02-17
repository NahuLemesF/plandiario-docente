# PlanDiario Docente - Backend

Backend del sistema PlanDiario Docente implementado con Spring Boot siguiendo Clean Architecture.

## Tecnologías

- Java 17
- Spring Boot 3.2.2
- Gradle 8.5
- H2 Database (desarrollo)
- Spring Data JPA
- Lombok

## Arquitectura

El proyecto sigue Clean Architecture con las siguientes capas:

- **Domain**: Núcleo del negocio, independiente de frameworks
- **Application**: Casos de uso y servicios de aplicación
- **Infrastructure**: Implementaciones de repositorios y adaptadores externos
- **Interfaces**: Controladores REST y DTOs

## Comandos

### Compilar el proyecto
```bash
./gradlew build
```

### Ejecutar tests
```bash
./gradlew test
```

### Levantar la aplicación
```bash
./gradlew bootRun
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints

### Health Check
```
GET /api/health
```

Respuesta:
```json
{
  "status": "ok"
}
```

## Base de datos

En desarrollo se utiliza H2 en memoria. La consola H2 está disponible en:
```
http://localhost:8080/h2-console
```

Credenciales:
- URL: `jdbc:h2:mem:plandiariodb`
- Usuario: `sa`
- Password: (vacío)

