# Coupon-Management API
 Creating a Coupon Management API's

## Overview

This project is a RESTful API designed to manage and apply various types of discount coupons for an e-commerce platform. The API is built using Spring Boot and uses MySQL as the database for storing coupon details and conditions. The system is designed with extensibility in mind, allowing for easy addition of new coupon types in the future.

## Features

- **Coupon Types Implemented**:
  - **Cart-wise**: Apply a discount to the entire cart if the total amount exceeds a certain threshold.
  - **Product-wise**: Apply a discount to specific products within the cart.
  - **BxGy**: "Buy X, Get Y" deals that can be applied to a set of products with a repetition limit.

- **API Endpoints**:
  - `POST /coupons`: Create a new coupon.
  - `GET /coupons`: Retrieve all coupons.
  - `GET /coupons/{id}`: Retrieve a specific coupon by its ID.
  - `PUT /coupons/{id}`: Update a specific coupon by its ID.
  - `DELETE /coupons/{id}`: Delete a specific coupon by its ID.
  - `POST /applicable-coupons`: Fetch all applicable coupons for a given cart and calculate the total discount.
  - `POST /apply-coupon/{id}`: Apply a specific coupon to the cart and return the updated cart with discounted prices.

## Implemented Cases

### 1. Cart-wise Discounts
- **Description**: A discount is applied to the total cart value if the cart total exceeds a predefined threshold.
- **Example**: If a coupon offers a 10% discount for carts over $100, and the cart total is $150, a $15 discount will be applied.

### 2. Product-wise Discounts
- **Description**: A discount is applied to specific products within the cart.
- **Example**: If a coupon offers a 20% discount on a specific product, and the product is in the cart, the discount will be applied to that product's price.

### 3. BxGy Discounts
- **Description**: "Buy X, Get Y" deals apply to sets of products with a limit on how many times the deal can be repeated.
- **Example**: If a coupon offers "Buy 2, Get 1 Free" on a set of products, and the customer buys 4 qualifying products, the customer will get 2 products for free.

## Unimplemented Cases

### 1. Complex Combinations of Coupon Types
- **Reason**: Combining multiple coupon types (e.g., applying both product-wise and cart-wise discounts together) was not implemented due to time constraints and the complexity of handling overlapping discounts.

### 2. Dynamic Coupon Conditions
- **Reason**: Coupons with highly dynamic conditions (e.g., time-based discounts, user-specific discounts) were not implemented as they require more sophisticated logic and context-aware decision-making.

## Limitations

- **Single Coupon Application**: The current implementation allows only one coupon to be applied to a cart at a time. Handling multiple coupons simultaneously is not supported.
- **Simple Validation**: Validation of coupon conditions is basic and does not account for complex scenarios such as different product categories or varying tax rates.
- **No User Context**: The system does not consider user-specific factors (e.g., user history, loyalty points) when applying discounts.

## Assumptions

- **Cart Structure**: It is assumed that the cart structure provided by the client includes a list of cart items and a cart total. Each cart item is expected to have a `productId`, `quantity`, and `price`.
- **Static Discounts**: It is assumed that discounts are static and do not vary dynamically based on factors like time, user history, or other conditions not stored in the coupon record.
- **No Currency Handling**: The system assumes that all prices and discounts are in the same currency and does not handle currency conversion.

## Setup Instructions

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher
- MySQL 5.7 or higher

### Running the Application

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   
### Update the MySQL configuration in src/main/resources/application.properties
- spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
- spring.datasource.username=root
- spring.datasource.password=yourpassword

### Build and run the application using Maven.
- mvn spring-boot:run


### Testing the API.
Use Postman or curl to interact with the API endpoints. Examples of request bodies and expected responses are documented above under "Implemented Cases."

### Future Enhancements.
Support for multiple coupon application scenarios.
Enhanced validation logic for more complex discount rules.
Addition of time-based, user-specific, and other dynamic coupon types.

### License
This project is licensed under the MIT License - see the LICENSE file for details.

Summary of the Documentation

This README file provides an overview of the Coupon Management API, explaining the features, implemented cases, unimplemented cases, limitations, assumptions, and instructions for setting up and running the application. The documentation is intended to give users and developers a clear understanding of how the API works and what to expect when using it. The README also outlines potential future enhancements, making it a comprehensive guide for both current and future development.
 