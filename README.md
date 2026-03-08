# Product Catalog Management System

## Project Overview  
This is a RESTful service for managing products and producers. It supports creating, retrieving, updating, and deleting products and producers, with support for flexible product attributes.

---

## Features

### Producers Endpoints (`/api/producer`)  
- **POST /api/producer**  
  Create a new producer with specified attributes.  

- **GET /api/producer**  
  Retrieve all producers along with their products and attributes.  

- **GET /api/producer/{producerId}**  
  Retrieve a single producer by its ID with associated products.  

- **DELETE /api/producer/{producerId}**  
  Delete a producer by ID (cascade deletes related products).  

---

### Products Endpoints (`/api/product`)  
- **POST /api/product**  
  Create a new product with attributes, associating it with a producer.  

- **GET /api/product**  
  Retrieve all products with their attributes.  

- **GET /api/product/{productId}**  
  Retrieve a single product by its ID with attributes.  

- **PATCH /api/product/{productId}**  
  Modify attributes or fields of an existing product.  

- **DELETE /api/product/{productId}**  
  Delete a product by its ID.  

---

## Technologies Used  
- Jav a25
- Spring Boot 3.5.11
- Spring JDBC Template  
- H2 Database
- Gson for JSON serialization  
- Liquibase for database migrations  

---

