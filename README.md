# PlanDiario Docente

Aplicación web para planificación diaria docente.

## Objetivo

Permitir a docentes crear, editar y visualizar planificaciones diarias organizadas por fecha, área y metas pedagógicas.

## Estado del proyecto

🚧 MVP en desarrollo

## Arquitectura

- Clean Architecture
- Spring Boot (Backend)
- Persistencia relacional (a definir)

## Arquitectura y Convenciones

Este proyecto sigue los principios de **Clean Architecture** (Arquitectura Limpia) para garantizar:
- Separación clara de responsabilidades
- Independencia de frameworks
- Testabilidad y mantenibilidad
- Escalabilidad futura

### Documentación Técnica

- **[Arquitectura del Sistema](docs/arquitectura.md)**: Estructura de capas, reglas de dependencias, políticas de excepciones y organización del dominio
- **[Convenciones de Desarrollo](docs/convenciones.md)**: Estrategia de branching, commits convencionales, nomenclatura y estilo de código

**Es fundamental revisar estos documentos antes de contribuir al proyecto.**

### Para Agentes IA

Si eres un agente de IA asistiendo en este proyecto:
1. Lee `docs/arquitectura.md` para entender la estructura de capas
2. Consulta `docs/convenciones.md` para nomenclatura y commits
3. Siempre empieza nuevas features desde el dominio hacia afuera
4. Respeta la regla de dependencias: solo hacia el núcleo (domain)

## Próximos pasos

- Definición formal del dominio
- Modelado de entidades
- Casos de uso principales