# 🌤 Weather API Wrapper Service
## 📌 Descripción
Este proyecto es un Weather API Wrapper, diseñado para obtener datos meteorológicos desde una API de terceros y mejorar el rendimiento mediante almacenamiento en caché con Redis.

## ⚙️ Tecnologías Utilizadas
### Java + Spring Boot 🖥️
### Redis 🏪 (para almacenamiento en caché)
### API de terceros (como Visual Crossing's API)

## 📌Arquitectura
### El sistema sigue este flujo:
1️⃣ Se consulta Redis para verificar si los datos del clima ya están almacenados.
2️⃣ Si existen en Redis, se devuelven directamente.
3️⃣ Si no, se solicita la información a la API de terceros.
4️⃣ Los datos obtenidos se almacenan en Redis con una expiración determinada.
5️⃣ Se devuelve la respuesta al usuario.


## 📡 Endpoints
http://localhost:8080/clima/{nombre_cuidad}
http://localhost:8080/clima/{nombre_cuidad}/all
