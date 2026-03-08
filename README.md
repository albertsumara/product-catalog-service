# Product Catalog Management System

## Project Overview  
This is a RESTful service for managing products and producers. It supports creating, retrieving, updating, and deleting products and producers, with support for flexible product attributes.

---

## Features

## Data Models

### Producer
Represents a product manufacturer. Each producer can have multiple products.  

**Fields:**  
- `id` (Long) – unique identifier  
- `name` (String) – producer name  
- `productList` (List<Product>) – list of products  

**Example JSON:**  
```json
{
  "id": 1,
  "name": "TechCorp"
}


---

### **Product**

```markdown id="product-model"
### Product
Represents a product linked to a producer.  

**Fields:**  
- `id` (Long) – unique product ID  
- `producerId` (Long) – ID of the producer  
- `name` (String) – product name  
- `price` (double) – product price  
- `attributes` (List<Attribute>) – product attributes  

**Example JSON:**  
```json
{
  "id": 101,
  "name": "Laptop X",
  "price": 2999.99,
  "RAM": "16GB",
  "Color": "Silver"
}


---

### **Attribute**

```markdown id="attribute-model"
### Attribute
Represents a key-value attribute for a product.  

**Fields:**  
- `id` (Long) – optional identifier  
- `name` (String) – attribute name  
- `value` (String) – attribute value  

**Example JSON:**  
```json
{
  "name": "RAM",
  "value": "16GB"
}

### Producers Endpoints (`/api/producer`)  
- **POST /api/producer**  
  Create a new producer with its name.  

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
- Java 25
- Spring Boot 3.5.11
- Spring JDBC Template  
- H2 Database
- Gson for JSON serialization  
- Liquibase for database migrations  

---

### Sample Data Initialization
The application includes a built-in data initializer that automatically populates the database with example producers, products, and their attributes on startup.  
This allows you to immediately test the API endpoints without manually creating any data.

## Requirements
- Java 25 (OpenJDK Temurin, LTS 2025)
- Maven 4+ or Gradle 8+
- Optional: IntelliJ IDEA or Eclipse for development

## Running the Application
1. Build the project:
   ```bash
   mvn clean install
