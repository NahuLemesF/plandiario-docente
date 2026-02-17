# Arquitectura del Sistema - PlanDiario Docente

## Visión General

Este proyecto implementa **Clean Architecture** (Arquitectura Limpia) para garantizar separación de responsabilidades, testabilidad y mantenibilidad a largo plazo.

## Capas de la Arquitectura

```
┌─────────────────────────────────────────────────┐
│          Interfaces (Presentation)              │
│  Controllers, DTOs, Exception Handlers          │
└────────────────┬────────────────────────────────┘
                 │ (depende de ↓)
┌────────────────▼────────────────────────────────┐
│             Application                         │
│  Use Cases, Services, Command/Query Handlers    │
└────────────────┬────────────────────────────────┘
                 │ (depende de ↓)
┌────────────────▼────────────────────────────────┐
│              Domain                             │
│  Entities, Value Objects, Repository Interfaces │
│  Business Rules, Domain Exceptions              │
└─────────────────────────────────────────────────┘
                 ▲
                 │ (implementa)
┌────────────────┴────────────────────────────────┐
│           Infrastructure                        │
│  JPA Entities, Repository Impl, External APIs   │
└─────────────────────────────────────────────────┘
```

### 1. Domain (Núcleo del Negocio)

**Responsabilidades:**
- Contiene las **Entidades de Dominio**: `PlanificacionDiaria`, `Meta`, `Actividad`, `Area`
- Define **Value Objects**: `Fecha`, `DescripcionActividad`, `NombreArea`
- Declara **interfaces de Repositorios** (no implementaciones)
- Implementa **reglas de negocio críticas**
- Lanza **excepciones de dominio** cuando se violan invariantes

**Ubicación:** `com.plandocente.domain`

**Ejemplos:**
- `com.plandocente.domain.model.PlanificacionDiaria`
- `com.plandocente.domain.model.Meta`
- `com.plandocente.domain.repository.PlanificacionRepository`
- `com.plandocente.domain.exception.FechaInvalidaException`

**Reglas:**
- **No depende de ninguna otra capa**
- **No conoce frameworks** (Spring, JPA, etc.)
- Validaciones propias del negocio (ej: una planificación no puede tener fecha futura mayor a 1 año)

### 2. Application (Casos de Uso)

**Responsabilidades:**
- Orquesta la lógica de aplicación
- Implementa **casos de uso específicos**: `CrearPlanificacionUseCase`, `ListarMetasPorFechaUseCase`
- Coordina entre repositorios y dominio
- Maneja transacciones (con anotaciones de Spring)
- Valida entrada de datos (complementario al dominio)

**Ubicación:** `com.plandocente.application`

**Ejemplos:**
- `com.plandocente.application.usecase.CrearPlanificacionUseCase`
- `com.plandocente.application.service.PlanificacionService`
- `com.plandocente.application.dto.CrearPlanificacionCommand`

**Reglas:**
- Depende **solo de Domain**
- No conoce detalles de infraestructura ni presentación

### 3. Infrastructure (Adaptadores de Salida)

**Responsabilidades:**
- Implementa **repositorios** usando JPA
- Define **entidades JPA** (mapeo a base de datos)
- Integración con servicios externos (si aplica)
- Configuración de persistencia

**Ubicación:** `com.plandocente.infrastructure`

**Ejemplos:**
- `com.plandocente.infrastructure.persistence.jpa.PlanificacionJpaEntity`
- `com.plandocente.infrastructure.persistence.repository.JpaPlanificacionRepository`
- `com.plandocente.infrastructure.persistence.adapter.PlanificacionRepositoryAdapter`

**Reglas:**
- Implementa interfaces definidas en **Domain**
- Mapea entre entidades de dominio y JPA
- Puede usar librerías de Spring Data JPA

### 4. Interfaces (Presentation / Adaptadores de Entrada)

**Responsabilidades:**
- Expone **REST Controllers**
- Define **DTOs de entrada/salida**
- Maneja **excepciones** con `@ControllerAdvice`
- Valida requests con Bean Validation
- Documentación OpenAPI/Swagger

**Ubicación:** `com.plandocente.interfaces`

**Ejemplos:**
- `com.plandocente.interfaces.rest.PlanificacionController`
- `com.plandocente.interfaces.dto.PlanificacionResponseDto`
- `com.plandocente.interfaces.exception.GlobalExceptionHandler`

**Reglas:**
- Depende de **Application** para ejecutar casos de uso
- Traduce excepciones de dominio/aplicación a HTTP responses
- No contiene lógica de negocio

## Regla de Dependencias

**Principio fundamental: Las dependencias apuntan SOLO HACIA ADENTRO**

```
Interfaces → Application → Domain
                ↑
Infrastructure ─┘
```

- **Domain**: independiente, sin dependencias externas
- **Application**: depende solo de Domain
- **Infrastructure**: implementa contratos de Domain
- **Interfaces**: depende de Application (y transitivamente de Domain)

## Validaciones y Reglas de Negocio

### En Domain
- Invariantes de entidades (ej: fecha no puede ser nula)
- Reglas críticas del negocio (ej: máximo 10 actividades por día)
- Value Objects auto-validantes

### En Application
- Validaciones de flujo (ej: usuario existe antes de asignar planificación)
- Coordinación entre agregados
- Reglas transversales

### En Interfaces
- Validación de formato (Bean Validation: `@NotNull`, `@Size`, etc.)
- Parseo de datos de entrada

## Política de Excepciones

### Dominio lanza:
- `DomainException` (abstracta)
  - `FechaInvalidaException`
  - `PlanificacionDuplicadaException`
  - `MetaInvalidaException`

### Application lanza:
- `ApplicationException`
  - `RecursoNoEncontradoException`
  - `OperacionNoPermitidaException`

### Interfaces captura con `@ControllerAdvice`:
- Traduce excepciones a:
  - `DomainException` → HTTP 400 (Bad Request)
  - `RecursoNoEncontradoException` → HTTP 404
  - `OperacionNoPermitidaException` → HTTP 403
  - Otros → HTTP 500

## Conceptos del Dominio

### PlanificacionDiaria
- **Entidad raíz**: Representa el plan de un día específico
- Contiene: fecha, lista de actividades, áreas involucradas
- Relación con Metas (objetivos a futuro asociados)

### Meta
- **Entidad**: Objetivo pedagógico a alcanzar
- Puede estar asociada a múltiples planificaciones
- Tiene fecha objetivo y descripción

### Actividad
- **Value Object o Entidad**: Tarea específica del día
- Pertenece a una planificación
- Incluye: descripción, área, duración estimada

### Area
- **Value Object**: Materia o disciplina (ej: Matemáticas, Lengua)
- Puede ser enum o entidad según complejidad futura

## Estructura de Paquetes

```
com.plandocente
├── domain
│   ├── model
│   │   ├── PlanificacionDiaria.java
│   │   ├── Meta.java
│   │   ├── Actividad.java
│   │   └── Area.java
│   ├── repository
│   │   ├── PlanificacionRepository.java
│   │   └── MetaRepository.java
│   └── exception
│       ├── DomainException.java
│       └── FechaInvalidaException.java
├── application
│   ├── usecase
│   │   ├── CrearPlanificacionUseCase.java
│   │   └── ListarPlanificacionesUseCase.java
│   ├── service
│   │   └── PlanificacionService.java
│   └── dto
│       └── CrearPlanificacionCommand.java
├── infrastructure
│   ├── persistence
│   │   ├── jpa
│   │   │   └── PlanificacionJpaEntity.java
│   │   ├── repository
│   │   │   └── JpaPlanificacionRepository.java
│   │   └── adapter
│   │       └── PlanificacionRepositoryAdapter.java
│   └── config
│       └── DatabaseConfig.java
└── interfaces
    ├── rest
    │   └── PlanificacionController.java
    ├── dto
    │   ├── PlanificacionRequestDto.java
    │   └── PlanificacionResponseDto.java
    └── exception
        └── GlobalExceptionHandler.java
```

## Base Package

**Package raíz definido:** `com.plandocente`

**Justificación:**
- Nombre corto y estable
- Independiente del branding comercial
- Refleja el dominio del negocio (planificación docente)
- Facilita refactorización futura sin cambios masivos

Todos los paquetes del proyecto deben seguir esta estructura:
- `com.plandocente.domain.*`
- `com.plandocente.application.*`
- `com.plandocente.infrastructure.*`
- `com.plandocente.interfaces.*`

## Principios de Diseño

1. **Dependency Inversion**: Dominio define contratos, infraestructura los implementa
2. **Single Responsibility**: Cada clase tiene una única razón para cambiar
3. **Open/Closed**: Abierto a extensión, cerrado a modificación
4. **No framework en el núcleo**: Domain es POJO puro
5. **Testabilidad**: Cada capa se prueba de forma aislada

## Notas para Agentes IA

- **Siempre empezar por Domain** al crear nuevas features
- Definir interfaces en Domain, implementar en Infrastructure
- Use Cases en Application orquestan, no contienen lógica de negocio
- Controllers son thin: validar, delegar, responder
- Excepciones de dominio viajan hasta la capa de presentación

