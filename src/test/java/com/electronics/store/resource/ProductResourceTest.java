package com.electronics.store.resource;

import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.Product;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.PatchProductDescriptionRequest;
import com.electronics.store.request.PatchProductPriceRequest;
import com.electronics.store.service.cart.CartService;
import com.electronics.store.service.product.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductResource.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    // DUMMY PRODUCT VALUES

    private static final String PRODUCT_ID = "1234";
    private static final String PRODUCT_NAME = "DUMMY PRODUCT";
    private static final String PRODUCT_DESCRIPTION = "This is the dummy description of the product";
    private static final Double PRODUCT_PRICE = 20.0;


    @Test
    void createProduct() throws Exception {
        //Arrange
        Product product = Product.builder().productId(PRODUCT_ID)
                .productName(PRODUCT_NAME)
                .productDescription(PRODUCT_DESCRIPTION)
                .productPrice(PRODUCT_PRICE).build();
        when(productService.create(any())).thenReturn(product);

        CreateProductRequest createProductRequest = CreateProductRequest.builder()
                .productName(PRODUCT_NAME).productDescription(PRODUCT_DESCRIPTION)
                .productPrice(PRODUCT_PRICE).build();
        String productJson = new ObjectMapper().writeValueAsString(createProductRequest);


        // ACT - ASSERT

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                .content(productJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").isNotEmpty());

    }

    @Test
    void getProducts() {

    }

    @Test
    void getProductByIdPositiveTest() throws Exception {
        //Arrange
        Product product = Product.builder().productId(PRODUCT_ID)
                .productName(PRODUCT_NAME)
                .productDescription(PRODUCT_DESCRIPTION)
                .productPrice(PRODUCT_PRICE).build();
        when(productService.getProductById(anyString())).thenReturn(product);
        // ACT - ASSERT

        mockMvc.perform(MockMvcRequestBuilders.get("/product/" +PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").isNotEmpty());
    }

    @Test
    void getProductByIdNegativeTest() throws Exception {
        //Arrange
        String fakeProductId = "FAKE_PRODUCT_ID";
        when(productService.getProductById(anyString())).thenThrow(ProductNotFoundException.class);

        // ACT - ASSERT
        Throwable exception = assertThrows(ProductNotFoundException.class, () -> productService.getProductById(fakeProductId));
        assertNotNull(exception);
    }

    @Test
    void patchProductDescription() throws Exception {
        //Arrange
        PatchProductDescriptionRequest patchProductDescriptionRequest =  PatchProductDescriptionRequest.builder().productDescription("This is the Changed Description").build();
        when(productService.changeProductDescription(anyString(),any())).thenReturn(Boolean.TRUE);
        String patchRequestJson = new ObjectMapper().writeValueAsString(patchProductDescriptionRequest);
        // ACT - ASSERT

        mockMvc.perform(MockMvcRequestBuilders.patch("/product/"+PRODUCT_ID+"/description")
                .content(patchRequestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(true + ""));
    }
    @Test
    void patchProductDescriptionNegativeTest() throws Exception {
        //Arrange
        String fakeProductId = "fakeId";
        PatchProductDescriptionRequest patchProductDescriptionRequest =  PatchProductDescriptionRequest.builder().productDescription("This is the Changed Description").build();
        when(productService.changeProductDescription(anyString(),any())).thenThrow(ProductNotFoundException.class);
        String patchRequestJson = new ObjectMapper().writeValueAsString(patchProductDescriptionRequest);
        // ACT - ASSERT

        mockMvc.perform(MockMvcRequestBuilders.patch("/product/"+fakeProductId+"/description")
                .content(patchRequestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchProductPrice() throws Exception {
        //Arrange
        PatchProductPriceRequest patchProductPriceRequest =  PatchProductPriceRequest.builder().productPrice(20.0).build();
        when(productService.changeProductPrice(anyString(),any())).thenReturn(Boolean.TRUE);
        String patchRequestJson = new ObjectMapper().writeValueAsString(patchProductPriceRequest);
        // ACT - ASSERT

        mockMvc.perform(MockMvcRequestBuilders.patch("/product/"+PRODUCT_ID+"/price")
                .content(patchRequestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(true + ""));
    }
    @Test
    void patchProductPriceNegativeTest() throws Exception {
        //Arrange

        String fakeProductId = "fakeId";
        PatchProductPriceRequest patchProductPriceRequest =  PatchProductPriceRequest.builder().productPrice(20.0).build();
        when(productService.changeProductPrice(anyString(),any())).thenThrow(ProductNotFoundException.class);
        String patchRequestJson = new ObjectMapper().writeValueAsString(patchProductPriceRequest);

        // ACT - ASSERT

        mockMvc.perform(MockMvcRequestBuilders.patch("/product/"+fakeProductId+"/price")
                .content(patchRequestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteProductById() {

    }

    @Test
    void deleteProductByIdPositiveTest() throws Exception {
        // ACT - ASSERT
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/" +PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}