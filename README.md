# IUD Asesorías - Sistema de Gestión de Proyectos

Sistema monolítico de gestión de asesorías universitarias desarrollado con Spring Boot 3, Java 17 y MySQL 8.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.2.0**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Actuator
- **MySQL 8.0**
- **Flyway** (Migraciones de BD)
- **Maven**
- **Swagger/OpenAPI** (Documentación API)
- **Docker & Docker Compose**
- **Lombok**
- **JUnit 5 & Mockito** (Testing)

## 📋 Características

### Entidades y Endpoints REST

El sistema gestiona las siguientes entidades a través de endpoints REST en `/api/v1`:

#### 1. Tipos de Proyecto (`/api/v1/tipos-proyecto`)
- GET - Listar todos (paginado)
- GET /{id} - Obtener por ID
- POST - Crear nuevo
- PUT /{id} - Actualizar
- DELETE /{id} - Eliminar

#### 2. Clientes (`/api/v1/clientes`)
- GET - Listar todos (paginado)
- GET /{id} - Obtener por ID
- POST - Crear nuevo
- PUT /{id} - Actualizar
- DELETE /{id} - Eliminar

#### 3. Universidades (`/api/v1/universidades`)
- GET - Listar todas (paginado)
- GET /{id} - Obtener por ID
- POST - Crear nueva
- PUT /{id} - Actualizar
- DELETE /{id} - Eliminar

#### 4. Etapas (`/api/v1/etapas`)
- GET - Listar todas (paginado)
- GET /{id} - Obtener por ID
- POST - Crear nueva
- PUT /{id} - Actualizar
- DELETE /{id} - Eliminar

#### 5. Proyectos (`/api/v1/proyectos`)
- GET - Listar todos (paginado con filtros)
- GET /{id} - Obtener por ID
- POST - Crear nuevo
- PUT /{id} - Actualizar
- DELETE /{id} - Eliminar

**Proyecto** incluye:
- `numero` (único)
- `titulo`
- `fechaInicio`
- `fechaEntrega`
- `valor` (BigDecimal)
- Referencias a: `tipoProyecto`, `cliente`, `universidad`, `etapa`

### Filtros y Paginación

Los proyectos soportan filtrado por:
- `titulo` (búsqueda parcial)
- `numero` (búsqueda parcial)
- `fechaInicio`
- `fechaEntrega`
- `tipoProyectoId`
- `clienteId`
- `universidadId`
- `etapaId`

Todos los endpoints soportan paginación con parámetros:
- `page` (número de página, default: 0)
- `size` (tamaño de página, default: 10)
- `sort` (campo de ordenamiento)

### Validaciones

- Validación de campos obligatorios
- Validación de unicidad (número de proyecto, documento de cliente, nombres)
- Validación de emails
- Validación de longitud de campos
- Validación de valores positivos

### Manejo de Errores

Respuestas de error estructuradas con:
- Timestamp
- Código de estado HTTP
- Mensaje de error
- Ruta de la petición
- Errores de validación detallados

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas:

```
src/main/java/com/iud/asesorias/
├── AsesoriasIuApplication.java
├── config/           # Configuraciones (OpenAPI)
├── controller/       # Controladores REST
├── dto/              # DTOs de Request/Response
├── exception/        # Excepciones personalizadas y manejadores
├── model/            # Entidades JPA
├── repository/       # Repositorios JPA
└── service/          # Lógica de negocio
```

## 🛠️ Configuración y Ejecución

### Prerequisitos

- Docker
- Docker Compose

### Ejecución con Docker Compose

1. Clonar el repositorio:
```bash
git clone <repository-url>
cd asesorias-iu
```

2. Construir y ejecutar:
```bash
docker compose up --build
```

La aplicación estará disponible en:
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs
- **Actuator**: http://localhost:8080/actuator

3. Para detener:
```bash
docker compose down
```

### Ejecución Local (sin Docker)

1. Tener MySQL 8 instalado y ejecutándose

2. Crear base de datos:
```sql
CREATE DATABASE asesorias_db;
```

3. Configurar variables de entorno o editar `application.yml`:
```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=asesorias_db
export DB_USER=root
export DB_PASSWORD=root
```

4. Ejecutar:
```bash
mvn clean install
mvn spring-boot:run
```

## 📊 Base de Datos

### Migraciones Flyway

Las migraciones se ejecutan automáticamente al iniciar la aplicación. Los scripts están en:
```
src/main/resources/db/migration/
```

### Esquema de Base de Datos

- `tipos_proyecto` - Tipos de proyecto
- `clientes` - Información de clientes
- `universidades` - Universidades
- `etapas` - Etapas de proyecto
- `proyectos` - Proyectos (con FK a las tablas anteriores)

## 🧪 Testing

Ejecutar tests:
```bash
mvn test
```

Los tests incluyen:
- Tests unitarios de servicios (Mockito)
- Tests de integración de controladores (MockMvc)

## 📝 Documentación API

### Swagger UI

Una vez la aplicación esté ejecutándose, acceder a:
- http://localhost:8080/swagger-ui.html

Aquí encontrarás la documentación interactiva de todos los endpoints.

### Postman Collection

Ver archivo: `postman_collection.json` (en el directorio raíz)

### Ejemplos de Uso

#### Crear un Tipo de Proyecto
```bash
curl -X POST http://localhost:8080/api/v1/tipos-proyecto \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Desarrollo Web",
    "descripcion": "Proyectos de desarrollo web"
  }'
```

#### Crear un Cliente
```bash
curl -X POST http://localhost:8080/api/v1/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "documento": "123456789",
    "email": "juan@example.com",
    "telefono": "3001234567",
    "direccion": "Calle 123"
  }'
```

#### Crear un Proyecto
```bash
curl -X POST http://localhost:8080/api/v1/proyectos \
  -H "Content-Type: application/json" \
  -d '{
    "numero": "PROJ-001",
    "titulo": "Sistema de Gestión",
    "fechaInicio": "2024-01-01",
    "fechaEntrega": "2024-06-30",
    "valor": 5000000.00,
    "tipoProyectoId": 1,
    "clienteId": 1,
    "universidadId": 1,
    "etapaId": 1
  }'
```

#### Listar Proyectos con Filtros
```bash
curl "http://localhost:8080/api/v1/proyectos?titulo=Sistema&page=0&size=10"
```

## 🔍 Actuator Endpoints

Monitoreo y métricas disponibles en `/actuator`:
- `/actuator/health` - Estado de salud de la aplicación
- `/actuator/info` - Información de la aplicación
- `/actuator/metrics` - Métricas de la aplicación

## 🐳 Docker

### Dockerfile

Usa multi-stage build para optimizar el tamaño de la imagen:
- Stage 1: Construcción con Maven y JDK 17
- Stage 2: Runtime con JRE 17 (imagen más liviana)

### Docker Compose

Servicios definidos:
- **db**: MySQL 8.0 con healthcheck
- **api**: Aplicación Spring Boot (depende de db)

Volúmenes:
- `mysql-data`: Persistencia de datos MySQL

Puertos expuestos:
- 3306: MySQL
- 8080: API

## 🔒 Seguridad

- Validación de entrada en todos los endpoints
- Manejo centralizado de excepciones
- Prepared statements (prevención de SQL injection)

## 📦 Estructura del Proyecto

```
asesorias-iu/
├── src/
│   ├── main/
│   │   ├── java/com/iud/asesorias/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── AsesoriasIuApplication.java
│   │   └── resources/
│   │       ├── db/migration/
│   │       └── application.yml
│   └── test/
│       └── java/com/iud/asesorias/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── postman_collection.json
└── README.md
```

## 👥 Contribución

1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia Apache 2.0.

## 📧 Contacto

IUD - info@iud.edu.co

---

**Nota**: Asegúrate de tener Docker y Docker Compose instalados para ejecutar la aplicación con el comando `docker compose up --build`.