# Guía de Pruebas de API con Postman

## Configuración inicial
Asegúrate de que tu backend Spring Boot esté corriendo en `http://localhost:8080`.

---

## 1. Login y obtención de JWT

**Método:** POST  
**URL:** `http://localhost:8080/api/auth/login`

### Usuarios disponibles:
Según tu `DataInitializer.java`, tienes estos usuarios creados automáticamente:

1. **Usuario ADMIN:**
   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```

2. **Usuario COORDINADOR:**
   ```json
   {
     "username": "coordinador",
     "password": "coord123"
   }
   ```

### Pasos:
1. Abre Postman
2. Crea una nueva petición POST
3. URL: `http://localhost:8080/api/auth/login`
4. Ve a **Body** → **raw** → **JSON**
5. Escribe el JSON de alguno de los usuarios
6. Haz clic en **Send**
7. Recibirás una respuesta como:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6...",
     "id": 1,
     "username": "admin",
     "email": "admin@example.com",
     "role": "ADMIN"
   }
   ```
8. **Copia el token** para usarlo en las siguientes peticiones

---

## 2. Configurar el Token en Postman

Para todos los endpoints protegidos:

### Opción 1: Agregar header manualmente
1. Ve a la pestaña **Headers**
2. Agrega:
   - **Key:** `Authorization`
   - **Value:** `Bearer <tu_token_jwt>`

### Opción 2: Usar variables de Postman (recomendado)
1. Después de hacer login, ve a la pestaña **Tests** de la petición de login
2. Agrega este script:
   ```javascript
   pm.environment.set("jwt_token", pm.response.json().token);
   ```
3. En las demás peticiones, usa en Authorization:
   - **Type:** Bearer Token
   - **Token:** `{{jwt_token}}`

---

## 3. Endpoints de Estadísticas

### 3.1. Obtener estadísticas generales
**Método:** GET  
**URL:** `http://localhost:8080/api/stats`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

**Respuesta esperada:**
```json
{
  "users": 2,
  "categories": 5,
  "subcategories": 10,
  "products": 15
}
```

---

## 4. Endpoints de Usuarios

### 4.1. Obtener todos los usuarios
**Método:** GET  
**URL:** `http://localhost:8080/api/users`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 4.2. Obtener un usuario por ID
**Método:** GET  
**URL:** `http://localhost:8080/api/users/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 4.3. Crear un nuevo usuario
**Método:** POST  
**URL:** `http://localhost:8080/api/users`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN

**Body (JSON):**
```json
{
  "username": "nuevo_usuario",
  "password": "password123",
  "email": "nuevo@example.com",
  "role": "COORDINATOR"
}
```

**Nota:** Los valores válidos para `role` son: `ADMIN` o `COORDINATOR`

---

### 4.4. Actualizar un usuario
**Método:** PUT  
**URL:** `http://localhost:8080/api/users/1`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN, COORDINATOR

**Body (JSON):**
```json
{
  "username": "usuario_actualizado",
  "email": "actualizado@example.com",
  "password": "nuevoPassword123",
  "role": "COORDINATOR"
}
```

---

### 4.5. Eliminar un usuario
**Método:** DELETE  
**URL:** `http://localhost:8080/api/users/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN

---

## 5. Endpoints de Categorías

### 5.1. Obtener todas las categorías
**Método:** GET  
**URL:** `http://localhost:8080/api/categories`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 5.2. Obtener una categoría por ID
**Método:** GET  
**URL:** `http://localhost:8080/api/categories/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 5.3. Crear una nueva categoría
**Método:** POST  
**URL:** `http://localhost:8080/api/categories`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN, COORDINATOR

**Body (JSON):**
```json
{
  "name": "Electrónica",
  "description": "Productos electrónicos y tecnológicos"
}
```

---

### 5.4. Actualizar una categoría
**Método:** PUT  
**URL:** `http://localhost:8080/api/categories/1`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN, COORDINATOR

**Body (JSON):**
```json
{
  "name": "Electrónica Actualizada",
  "description": "Descripción actualizada"
}
```

---

### 5.5. Eliminar una categoría
**Método:** DELETE  
**URL:** `http://localhost:8080/api/categories/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN

---

## 6. Endpoints de Subcategorías

### 6.1. Obtener todas las subcategorías
**Método:** GET  
**URL:** `http://localhost:8080/api/subcategories`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 6.2. Obtener una subcategoría por ID
**Método:** GET  
**URL:** `http://localhost:8080/api/subcategories/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 6.3. Obtener subcategorías por categoría
**Método:** GET  
**URL:** `http://localhost:8080/api/subcategories/category/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 6.4. Crear una nueva subcategoría
**Método:** POST  
**URL:** `http://localhost:8080/api/subcategories`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN, COORDINATOR

**Body (JSON):**
```json
{
  "name": "Laptops",
  "description": "Computadoras portátiles",
  "categoryId": 1
}
```

---

### 6.5. Actualizar una subcategoría
**Método:** PUT  
**URL:** `http://localhost:8080/api/subcategories/1`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN, COORDINATOR

**Body (JSON):**
```json
{
  "name": "Laptops Gaming",
  "description": "Laptops para videojuegos de alto rendimiento",
  "categoryId": 1
}
```

---

### 6.6. Eliminar una subcategoría
**Método:** DELETE  
**URL:** `http://localhost:8080/api/subcategories/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN

---

## 7. Endpoints de Productos

### 7.1. Obtener todos los productos
**Método:** GET  
**URL:** `http://localhost:8080/api/products`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 7.2. Obtener un producto por ID
**Método:** GET  
**URL:** `http://localhost:8080/api/products/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 7.3. Obtener productos por categoría
**Método:** GET  
**URL:** `http://localhost:8080/api/products/category/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 7.4. Obtener productos por subcategoría
**Método:** GET  
**URL:** `http://localhost:8080/api/products/subcategory/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN, COORDINATOR

---

### 7.5. Crear un nuevo producto
**Método:** POST  
**URL:** `http://localhost:8080/api/products`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN, COORDINATOR

**Body (JSON):**
```json
{
  "name": "Laptop Dell XPS 15",
  "description": "Laptop de alto rendimiento para profesionales",
  "price": 1500000,
  "stock": 10,
  "categoryId": 1,
  "subcategoryId": 1,
  "imageUrl": "https://example.com/laptop-dell-xps15.jpg"
}
```

---

### 7.6. Actualizar un producto
**Método:** PUT  
**URL:** `http://localhost:8080/api/products/1`

**Headers:**
```
Authorization: Bearer <tu_token>
Content-Type: application/json
```

**Roles permitidos:** ADMIN, COORDINATOR

**Body (JSON):**
```json
{
  "name": "Laptop Dell XPS 15 2024",
  "description": "Última generación con mejor rendimiento",
  "price": 1600000,
  "stock": 8,
  "categoryId": 1,
  "subcategoryId": 1,
  "imageUrl": "https://example.com/laptop-dell-xps15-2024.jpg"
}
```

---

### 7.7. Eliminar un producto
**Método:** DELETE  
**URL:** `http://localhost:8080/api/products/1`

**Headers:**
```
Authorization: Bearer <tu_token>
```

**Roles permitidos:** ADMIN

---

## 8. Resumen de Permisos

| Rol | Permisos |
|-----|----------|
| **ADMIN** | Acceso completo: crear, leer, actualizar y eliminar todos los recursos |
| **COORDINATOR** | Puede crear, leer y actualizar recursos, pero NO puede eliminar |

---

## 9. Flujo de Prueba Recomendado

### Paso 1: Autenticación
1. Login como ADMIN (`admin` / `admin123`)
2. Guardar el token

### Paso 2: Ver estadísticas iniciales
3. GET `/api/stats` - Ver contadores iniciales

### Paso 3: Crear estructura de datos
4. POST `/api/categories` - Crear categoría "Electrónica"
5. POST `/api/subcategories` - Crear subcategoría "Laptops" (usar ID de categoría creada)
6. POST `/api/products` - Crear producto asociado

### Paso 4: Consultas
7. GET `/api/categories` - Listar todas las categorías
8. GET `/api/subcategories/category/{id}` - Ver subcategorías de una categoría
9. GET `/api/products/category/{id}` - Ver productos de una categoría
10. GET `/api/products/subcategory/{id}` - Ver productos de una subcategoría

### Paso 5: Actualizaciones
11. PUT `/api/products/{id}` - Actualizar precio y stock
12. GET `/api/stats` - Ver estadísticas actualizadas

### Paso 6: Probar permisos
13. Login como COORDINATOR (`coordinador` / `coord123`)
14. Intentar DELETE `/api/products/{id}` - Debe fallar (403 Forbidden)
15. Login como ADMIN nuevamente
16. DELETE `/api/products/{id}` - Debe funcionar

---

## 10. Respuestas de Error Comunes

| Código | Significado | Causa |
|--------|-------------|-------|
| **401 Unauthorized** | Token inválido o no proporcionado | Falta el header Authorization o el token expiró |
| **403 Forbidden** | Sin permisos | Tu rol no tiene acceso a esta operación |
| **404 Not Found** | Recurso no encontrado | El ID no existe en la base de datos |
| **400 Bad Request** | Datos incorrectos | El JSON está mal formado o faltan campos requeridos |
| **500 Internal Server Error** | Error del servidor | Problema en el backend (revisar logs) |

---

## 11. Tips para Postman

### Crear una colección
1. Agrupa todos los endpoints en una colección
2. Usa carpetas para organizar por recurso (Users, Categories, Products, etc.)

### Variables de entorno
Crea variables para:
- `base_url`: `http://localhost:8080`
- `jwt_token`: (se actualiza automáticamente con el script de Tests)

### Scripts útiles

**En la petición de Login (Tests):**
```javascript
if (pm.response.code === 200) {
    pm.environment.set("jwt_token", pm.response.json().token);
    console.log("Token guardado:", pm.response.json().token);
}
```

**Para usar el token automáticamente:**
En cada petición, en la pestaña **Authorization**:
- Type: Bearer Token
- Token: `{{jwt_token}}`

---

## 12. Solución de Problemas

### Si Postman se queda en "Sending request..."

1. **Verificar que el backend esté corriendo:**
   ```cmd
   cd C:\xampp1\htdocs\app12_nuevo\backend
   mvnw spring-boot:run
   ```

2. **Verificar que MySQL esté corriendo:**
   - Abre XAMPP Control Panel
   - Verifica que MySQL esté iniciado

3. **Verificar la URL:**
   - Usa `http://localhost:8080` (no `https`)
   - No uses variables hasta confirmar que funciona

4. **Probar primero con una petición simple:**
   - GET `http://localhost:8080/api/stats`
   - Si obtienes 401, el servidor funciona

5. **Revisar logs del backend:**
   - Busca errores en la terminal donde corre Spring Boot

---

¡Listo! Con esta guía puedes probar todos los endpoints de tu API. 🚀
