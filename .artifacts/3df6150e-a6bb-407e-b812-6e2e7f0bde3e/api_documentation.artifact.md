# KMPStarter REST API Documentation

Production-ready Ktor backend REST API designed for multiplatform consumption across Android, iOS, Desktop (JVM), and Web (WasmJS).

## Base URL
- **Local Development**: `http://localhost:8080`
- **Android Emulator**: `http://10.0.2.2:8080`
- **Physical Device (Same Wi-Fi)**: `http://<your-machine-ip>:8080`

---

## Endpoints

### 1. Health Check
- **URL**: `/health`
- **Method**: `GET`
- **Description**: Returns server status and health information.
- **Response (`200 OK`)**:
```json
{
  "status": "UP",
  "service": "KMPStarter Backend Server"
}
```

---

### 2. Get All Items
- **URL**: `/api/v1/items`
- **Method**: `GET`
- **Description**: Retrieves all items sorted by creation time descending.
- **Response (`200 OK`)**:
```json
[
  {
    "id": 2,
    "title": "Ktor REST API",
    "description": "Build production-ready backend services with Kotlin and Ktor.",
    "createdAt": 1740000000000
  },
  {
    "id": 1,
    "title": "KMPStarter Architecture",
    "description": "Explore multiplatform clean architecture across Android, iOS, Desktop, and Web.",
    "createdAt": 1740000000000
  }
]
```

---

### 3. Create Item
- **URL**: `/api/v1/items`
- **Method**: `POST`
- **Description**: Creates a new item.
- **Request Body (`application/json`)**:
```json
{
  "title": "New Multiplatform Feature",
  "description": "Implement shared API client repository."
}
```
- **Response (`201 Created`)**:
```json
{
  "id": 3,
  "title": "New Multiplatform Feature",
  "description": "Implement shared API client repository.",
  "createdAt": 1740000100000
}
```
- **Error (`400 Bad Request`)**:
```json
{
  "code": 400,
  "message": "Title cannot be blank"
}
```

---

### 4. Get Item by ID
- **URL**: `/api/v1/items/{id}`
- **Method**: `GET`
- **Description**: Retrieves a single item by its ID.
- **Response (`200 OK`)**:
```json
{
  "id": 1,
  "title": "KMPStarter Architecture",
  "description": "Explore multiplatform clean architecture across Android, iOS, Desktop, and Web.",
  "createdAt": 1740000000000
}
```
- **Error (`404 Not Found`)**:
```json
{
  "code": 404,
  "message": "Item with ID 999 not found"
}
```

---

### 5. Update Item
- **URL**: `/api/v1/items/{id}`
- **Method**: `PUT`
- **Description**: Updates an existing item (partial updates supported).
- **Request Body (`application/json`)**:
```json
{
  "title": "Updated Title",
  "description": "Updated description."
}
```
- **Response (`200 OK`)**:
```json
{
  "id": 1,
  "title": "Updated Title",
  "description": "Updated description.",
  "createdAt": 1740000000000
}
```

---

### 6. Delete Item
- **URL**: `/api/v1/items/{id}`
- **Method**: `DELETE`
- **Description**: Deletes an item by ID.
- **Response (`200 OK`)**:
```json
{
  "success": true,
  "deletedId": 1
}
```

---

## Consuming in KMP (`core/network` or Shared Code)

To consume this API across Android, iOS, Desktop, and Web, define a shared data model and service in `core/network` using Ktor Client:

```kotlin
package template.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: Long
)

@Serializable
data class ItemCreateRequestDto(
    val title: String,
    val description: String
)

class ItemApiService(private val client: HttpClient, private val baseUrl: String = "http://localhost:8080") {
    suspend fun getItems(): List<ItemDto> = client.get("$baseUrl/api/v1/items").body()

    suspend fun createItem(title: String, description: String): ItemDto =
        client.post("$baseUrl/api/v1/items") {
            setBody(ItemCreateRequestDto(title, description))
        }.body()

    suspend fun deleteItem(id: Long) = client.delete("$baseUrl/api/v1/items/$id")
}
```
