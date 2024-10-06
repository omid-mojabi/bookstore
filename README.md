# Bookstore API

## Overview


The **Bookstore** API is a REST service for managing an online bookstore. It allows clients to list, filter, and purchase books. The API includes various features like database management, book cover image storage, and more.

This project is built using Java and Spring Boot, with Maven as a build tool. The API follows a clean architecture and integrates with an H2 in-memory database for testing purposes, though it can be adapted to work with other database systems.

## Features

- **List Books:** View all available books in the bookstore.
- **Purchase Books:** Purchase available books from the store.
- **Filter Books:** Filter books by title, author, or genre.
- **Book Management:** Add, or edit books from the database.
- **Image Storage:** Upload and store book cover images.
- **Validation:** Ensure that data conforms to validation rules before database persistence.

## Technologies Used

- **Java 21**
- **Spring Boot 3.3.2**
- **Spring Data JPA**
- **Hibernate**
- **Maven**
- **H2 Database (for testing)**
- **Mockito (for testing)**
- **Lombok (for code reduction)**

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/bookstore.git
2. **Navigate to the project directory:**
   ```bash
   cd bookstore
3. **Build the project using Maven:**
   ```bash
   mvn clean install
4. **Run the application:**
   ```bash
   mvn spring-boot:run

The application will be running on http://localhost:8080.

Swagger documentation address: http://localhost:8080/swagger-ui/index.html#/


Here is the ER Diagram of Bookstore API elements:
## Entity Relationship Diagram (ERD)

```mermaid
erDiagram
  BOOK {
    Long Id
    String Title
    String Category
    BigDecimal Price
    Integer Stock
    byte[] Cover_Image
  }

  AUTHOR {
    Long Id
    String Name
  }

  BOOK_AUTHOR {
    Long Book_Id
    Long Author_Id
  }

  CART_ITEM {
    Long Id
    Date Date_Time
    Integer Quantity
    Long Book_Id
  }

  SHOPPING_CART {
    Long Id
    Date Date_Time
    BigDecimal Sum
  }

  SHOPPING_CART_ITEM {
    Long Shopping_Cart_Id
    Long Cart_Item_Id
  }

  BOOK ||--o{ BOOK_AUTHOR : "has"
  AUTHOR ||--o{ BOOK_AUTHOR : "written_by"
  BOOK ||--o{ CART_ITEM : "contains"
  SHOPPING_CART ||--o{ SHOPPING_CART_ITEM : "includes"
  CART_ITEM ||--o{ SHOPPING_CART_ITEM : "part_of"
