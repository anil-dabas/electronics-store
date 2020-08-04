package com.electronics.store.service.product;

import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.Product;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.PatchProductDescriptionRequest;
import com.electronics.store.request.PatchProductPriceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    private static final String DUMMY_DESCRIPTION = "This is a dummy product used for Unit tests ";
    private static final Double DUMMY_PRICE = 20.0;
    private static final String DUMMY_PRODUCT = "Dummy Product";
    @Autowired
    ProductService productService;

    @Test
    void createProductTest() throws ProductNotFoundException {
        Product product = createProduct();

        // This is a repository test there is no need to create a separate test as there is no implementation
        Product repositoryTest = productService.getProductById(product.getProductId());

        // Assert

        assertNotNull(product);
        assertEquals(DUMMY_PRODUCT,product.getProductName());
        assertEquals(repositoryTest.getProductId(),product.getProductId());
    }

    @Test
    void getProductById() throws ProductNotFoundException {
        Product product = createProduct();
        Product resultingProduct = productService.getProductById(product.getProductId());
        assertNotNull(resultingProduct);
        assertEquals(resultingProduct.getProductName(),product.getProductName());
        assertEquals(resultingProduct.getProductId(),product.getProductId());
    }

    @Test
    void getAllProducts() {
        Product product = createProduct();
        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
    }

    @Test
    void changeProductDescription() throws ProductNotFoundException {
        Product product = createProduct();
        String newDescription = "This is the new Description";
        PatchProductDescriptionRequest patchProductDescriptionRequest = PatchProductDescriptionRequest.builder()
                .productDescription(newDescription).build();
        productService.changeProductDescription(product.getProductId(),patchProductDescriptionRequest);
        Product resultingProduct = productService.getProductById(product.getProductId());
                assertNotNull(resultingProduct);
        assertEquals(resultingProduct.getProductDescription(),newDescription);
        assertEquals(resultingProduct.getProductId(),product.getProductId());
    }
    @Test
    void changeProductPrice() throws ProductNotFoundException {
        Product product = createProduct();
        Double newPrice = 40.0;
        PatchProductPriceRequest patchProductPriceRequest = PatchProductPriceRequest.builder()
                .productPrice(newPrice).build();
        productService.changeProductPrice(product.getProductId(),patchProductPriceRequest);
        Product resultingProduct = productService.getProductById(product.getProductId());
        assertNotNull(resultingProduct);
        assertEquals(resultingProduct.getProductPrice(),newPrice);
        assertEquals(resultingProduct.getProductId(),product.getProductId());
    }

    @Test
    void deleteProduct() throws ProductNotFoundException {
        Product product = createProduct();
        productService.deleteProduct(product.getProductId());
        Throwable exception = assertThrows(ProductNotFoundException.class, ()->productService.getProductById(product.getProductId()));
        assertNotNull(exception);
    }

    private Product createProduct(){
        CreateProductRequest createProductRequest = CreateProductRequest.builder().productName(DUMMY_PRODUCT)
                .productDescription(DUMMY_DESCRIPTION).productPrice(DUMMY_PRICE).build();
        return productService.create(createProductRequest);
    }
}