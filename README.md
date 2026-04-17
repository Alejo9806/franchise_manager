# 🏢 Franchise Manager API

[![Deploy to Railway](https://img.shields.io/badge/Railway-000000?style=for-the-badge&logo=railway&logoColor=white)](https://franchisemanager-production.up.railway.app/)
[![Java 21](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/technologies/downloads/#java21)
[![Spring Boot 3.5.13](https://img.shields.io/badge/Spring_Boot-3.5.13-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)

API diseñada para la gestión jerárquica de franquicias, sucursales y productos. Implementada siguiendo principios de **Clean Architecture** y **Domain-Driven Design (DDD)** para garantizar escalabilidad, mantenibilidad y una lógica de negocio desacoplada de la infraestructura.

---

## 🔗 Despliegue Online
Accede a la instancia de producción para pruebas directas:
🚀 **[https://franchisemanager-production.up.railway.app](https://franchisemanager-production.up.railway.app/franchises)**

---

## 🏗️ Arquitectura y Diseño
El proyecto se organiza en 4 capas fundamentales:

| Capa | Responsabilidad |
| :--- | :--- |
| **Dominio** | Modelos (`Franchise`, `Branch`, `Product`), lógica de negocio pura e interfaces de repositorios. Sin dependencias externas. |
| **Aplicación** | Implementación de Casos de Uso (Use Cases) y definición de DTOs para comunicación externa. |
| **Infraestructura** | Persistencia JPA (Postgres), configuración de Spring Boot y adaptadores técnicos. |
| **Presentación** | Controladores REST y Mappers (MapStruct) para la transformación de datos. |

---

## 🔐 Variables de Entorno y Configuración
El proyecto utiliza variables de entorno para gestionar la conexión a la base de datos. Por seguridad, no se incluyen valores por defecto en el código.

### Diccionario de variables:
- `DB_HOST`: Host de la base de datos (ej: `localhost` o `db` en Docker).
- `DB_PORT`: Puerto (ej: `5432`).
- `DB_NAME`: Nombre de la base de datos (ej: `franchise`).
- `DB_USER`: Nombre de usuario del administrador de DB.
- `DB_PASSWORD`: Contraseña del usuario.

---

## 🚀 Inicio Local (Sin Docker)
Si deseas ejecutar la aplicación directamente en tu máquina host, sigue estos pasos:

### 1. Entorno
Debes tener instalado **Java 21** y **Maven 3.x** corriendo localmente.

### 2. Base de Datos
Debes tener instalado **PostgreSQL** (v16 recomendado) corriendo localmente. Crea una base de datos nueva (ej: `franchise`).

### 3. Configuración de Entorno (.env)
El proyecto utiliza `spring.config.import` para leer un archivo `.env` automáticamente. Crea un archivo llamado **`.env`** en la raíz del proyecto con el siguiente contenido:

```properties
DB_HOST=localhost
DB_PORT=5432
DB_NAME=tu_base_de_datos
DB_USER=tu_usuario
DB_PASSWORD=tu_contrasena
```

### 3. Ejecución
Una vez configurado el `.env`:
```bash
# Compilar y ejecutar
./mvnw spring-boot:run
```
La aplicación se conectará usando las variables del archivo `.env` y creará las tablas necesarias automáticamente (`ddl-auto=update`).

---

## 🐳 Levantamiento con Docker (Entorno Completo)

El proyecto está totalmente contenedorizado utilizando un enfoque de **Multi-stage Build** en el `Dockerfile` y orquestación con `docker-compose`.

### 1. El Dockerfile (Optimización de Imagen)
- **Stage 1 (Build)**: Usa `maven:3.9.6` para compilar y generar el `.jar`.
- **Stage 2 (Runtime)**: Usa un JRE ligero para ejecutar la aplicación, lo que reduce el tamaño de la imagen final y mejora la seguridad.

### 2. Redes y Conectividad (Docker Compose)
Se crea una red bridge llamada `franchise_net`:
- La API se conecta a la base de datos usando el host `db` (nombre del servicio en el compose).
- El archivo `docker-compose.yml` ya tiene los valores de entorno pre-configurados para que funcionen inmediatamente.

### Pasos para levantar el entorno:
```bash
# Construir la imagen de la API y levantar los servicios (DB + App)
docker-compose up --build
```

---

## 🧪 Pruebas Unitarias
Se han implementado pruebas exhaustivas para validar la lógica de negocio y los controladores.
```bash
./mvnw test
```

---

## 📡 Documentación de Endpoints

### 🏢 Franquicias
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/franchises` | Crear Franquicia. |
| **GET** | `/franchises` | Listar todas las franquicias y sucursales. |
| **PATCH** | `/franchises/{id}`| Actualizar nombre de franquicia. |
| **GET** | `/franchises/top-products/{id}`| Producto con mayor stock por sucursal. |

### 📍 Sucursales
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/branches/{franchiseId}` | Agregar sucursal a una franquicia. |
| **PATCH** | `/branches/{branchId}` | Actualizar nombre de sucursal. |

### 📦 Productos
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/products/{branchId}` | Agregar producto a una sucursal. |
| **PATCH** | `/products/{productId}/stock` | Modificar stock. |
| **PATCH** | `/products/{productId}/name` | Modificar nombre del producto. |
| **DELETE** | `/products/{productId}` | Eliminar producto. |

---

## 📬 Guía Detallada de Consumo

### 🏗️ Gestión de Franquicias

**Crear una Franquicia:**
```json
// POST /franchises
{
  "name": "Franquicia Horizonte"
}
```

**Actualizar Nombre de Franquicia:**
```json
// PATCH /franchises/1
{
  "name": "Franquicia Horizonte Renovada"
}
```

**Listar todas las Franquicias:**
```bash
// GET /franchises
# No requiere Body. Retorna lista jerárquica: Franquicias -> Sucursales -> Productos.
```

**Obtener Productos con mayor Stock:**
```bash
// GET /franchises/top-products/1
# Retorna el producto con el stock máximo por cada sucursal de la franquicia con ID 1.
```

---

### 🏛️ Gestión de Sucursales

**Agregar Sucursal a una Franquicia:**
```json
// POST /branches/1
{
  "name": "Sucursal Norte Medellín"
}
```

**Actualizar Nombre de Sucursal:**
```json
// PATCH /branches/5
{
  "name": "Sucursal Medellín - Belén"
}
```

---

### 📦 Gestión de Inventario (Productos)

**Agregar Producto a una Sucursal:**
```json
// POST /products/1
{
  "name": "Coca Cola 500ml",
  "stock": 24
}
```

**Modificar Stock de Producto:**
```json
// PATCH /products/10/stock
{
  "stock": 150
}
```

**Actualizar Nombre de Producto:**
```json
// PATCH /products/10/name
{
  "name": "Coca Cola 500ml Light"
}
```

**Eliminar un Producto:**
```bash
// DELETE /products/10
# No requiere Body. Elimina el producto del sistema.
```
