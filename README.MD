#Electronics Store

This is a electronic store with basic functionality as below 

##Store owner:
- Create a new product
- Amend the price/description of a product
- Remove a product
- Apply discount deals for products (eg buy 1 get 50% off the second)
- Apply bundle deals (eg buy 1 laptop, get a mouse for free)


##Checkout:
- Add product to basket
- Amend quantity of product in a basket
- Remove product from basket
- Calculate total price and products taking into account all discounts and bundles 

## Basic Technologies & Approach Used

- Programming Language - Core Java 8
- SpringBoot 
- REST API
- Open API 3.0 for Swagger UI Implementation
- Test Driven Development (TDD) with JUnit 5
- Database - In-memory H2 database

## How to 
- Run the Application
    1. Download the application in Directory from Git Hub 
    2. Run the following command from project root (Using your Terminal or Command Prompt)
    
        ```mvn clean compile spring-boot:run```
    3. The application will run on Port 8080 (Please keep port 8080 free)
    4. The Custom Swagger UI URL is 
        http://localhost:8080/ui.html
        
- Run the test 
    1. Same as Run the Application 
    2. Run following command from the project root 
    
        ```mvn clean test```
    3. The Test output will come on command line 