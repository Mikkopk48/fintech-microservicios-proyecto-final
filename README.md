# FinTech Microservicios — Proyecto final de la diplomatura

Sistema de gestión de clientes y productos financieros construido con microservicios en Spring Boot y Spring Cloud. Es
la actividad final de la Diplomatura en Desarrollo de Software FinTech: IA y Microservicios.

## Arquitectura

El sistema tiene cuatro aplicaciones independientes y un repositorio de configuración:

| Módulo                 | Puerto | Función                                                                          |
|------------------------|--------|----------------------------------------------------------------------------------|
| config-server-diplo    | 8888   | Centraliza la configuración. La lee del repositorio remoto fintech-config-diplo. |
| eureka-server-diplo    | 8761   | Registro y descubrimiento de servicios.                                          |
| product-service-diplo  | 8082   | Gestiona productos financieros: cuentas, tarjetas, préstamos e inversiones.      |
| customer-service-diplo | 8081   | Gestiona clientes y consulta sus productos a product-service mediante Feign.     |

Repositorio de configuración: https://github.com/Mikkopk48/fintech-config-diplo

Flujo principal:

1. Al arrancar, cada servicio pide su configuración al Config Server, que la lee desde GitHub.
2. Los servicios se registran en Eureka con los nombres `product-service` y `customer-service`.
3. Cuando se consulta un cliente, customer-service usa un cliente Feign: le pide a Eureka la dirección de
   product-service y obtiene los productos de ese cliente por HTTP.

Cada servicio tiene su propia base de datos H2 en memoria (`customerdb` y `productdb`). No hay claves foráneas entre
servicios: la relación se mantiene guardando el `customerId` en cada producto.

## Tecnologías

- Java 21
- Spring Boot 4.0.9
- Spring Cloud Config (servidor y cliente)
- Spring Cloud Netflix Eureka
- Spring Cloud OpenFeign
- Spring Data JPA con H2
- Jakarta Validation
- Lombok
- Maven

## Estructura de cada servicio

Ambos servicios siguen la arquitectura en capas de Spring:

- `controller`: recibe los pedidos HTTP y delega en el service.
- `service`: interfaz e implementación con la lógica de negocio.
- `repository`: acceso a datos con Spring Data JPA.
- `entity`: entidades JPA.
- `dto`: objetos de entrada y salida de la API, con validaciones.
- `mapper`: conversión entre DTOs y entidades.
- `exception`: excepciones propias y un `GlobalExceptionHandler` que devuelve errores en formato JSON.
- `client` (solo customer-service): interfaz Feign `ProductClient`.

## Requisitos

- JDK 21 o superior
- Git
- Postman u otra herramienta para probar la API

No hace falta instalar Maven: cada módulo incluye el wrapper `mvnw`.

## Cómo ejecutar

Los servicios deben levantarse en este orden, esperando que cada uno termine de arrancar antes del siguiente:

```bash
cd config-server-diplo && ./mvnw spring-boot:run
cd eureka-server-diplo && ./mvnw spring-boot:run
cd product-service-diplo && ./mvnw spring-boot:run
cd customer-service-diplo && ./mvnw spring-boot:run
```

En Windows se usa `mvnw.cmd` en lugar de `./mvnw`. Cada comando va en una terminal distinta.

### Verificación

- Config Server: abrir `http://localhost:8888/customer-service/default` y ver la configuración en JSON.
- Eureka: abrir `http://localhost:8761` y comprobar que aparecen PRODUCT-SERVICE y CUSTOMER-SERVICE con estado UP.

## Endpoints

### customer-service (puerto 8081)

| Método | Ruta            | Descripción                           | Éxito | Errores       |
|--------|-----------------|---------------------------------------|-------|---------------|
| POST   | /customers      | Registra un cliente                   | 201   | 400, 409      |
| GET    | /customers/{id} | Consulta un cliente con sus productos | 200   | 404, 503      |
| GET    | /customers      | Lista todos los clientes              | 200   | —             |
| PUT    | /customers/{id} | Actualiza un cliente                  | 200   | 400, 404, 409 |

### product-service (puerto 8082)

| Método | Ruta                            | Descripción               | Éxito | Errores  |
|--------|---------------------------------|---------------------------|-------|----------|
| POST   | /products                       | Crea un producto          | 201   | 400, 409 |
| GET    | /products/customer/{customerId} | Productos de un cliente   | 200   | —        |
| GET    | /products/{id}                  | Consulta un producto      | 200   | 404      |
| GET    | /products                       | Lista todos los productos | 200   | —        |

Tipos de producto válidos: `CUENTA`, `TARJETA`, `PRESTAMO`, `INVERSION`.

## Prueba de integración

1. Crear un cliente con `POST http://localhost:8081/customers`:

```json
{
  "name": "Ana López",
  "document": "40123456",
  "email": "ana@mail.com",
  "balance": 150000.00
}
```

2. Crear un producto para ese cliente con `POST http://localhost:8082/products`:

```json
{
  "customerId": 1,
  "type": "CUENTA",
  "productNumber": "0001-123",
  "amount": 150000.00
}
```

3. Consultar el cliente con `GET http://localhost:8081/customers/1`. La respuesta incluye los datos del cliente y la
   lista `products`, que customer-service obtuvo de product-service a través de Feign y Eureka.

## Manejo de errores

Todos los errores se devuelven con el mismo formato:

```json
{
  "timestamp": "2026-11-19T16:10:00",
  "status": 404,
  "error": "Not Found",
  "message": "No existe el cliente con id 999"
}
```

| Código | Cuándo ocurre                                                   |
|--------|-----------------------------------------------------------------|
| 400    | Los datos enviados no pasan las validaciones                    |
| 404    | El cliente o el producto no existe                              |
| 409    | Documento, correo o número de producto repetido                 |
| 503    | product-service no responde cuando customer-service lo consulta |

## Notas

- Las bases H2 son en memoria: los datos se pierden al reiniciar cada servicio.
- La consola de H2 está habilitada en `/h2-console` de cada servicio.
- No se implementaron borrados: en sistemas financieros se prefiere el borrado lógico, que queda como mejora futura.
- Toda la configuración (puertos, URLs, base de datos) vive en el repositorio fintech-config-diplo. Cada servicio solo conserva localmente su nombre y la dirección del Config Server, porque los necesita para pedir su configuración al arrancar. El Config Server guarda su propia configuración localmente, ya que es la fuente de configuración.
- Después de levantar los cuatro servicios, esperar unos 30 segundos antes de probar


### Para liberar todos los puretos de forma rápida

```
fuser -k 8888/tcp 8761/tcp 8081/tcp 8082/tcp
```
