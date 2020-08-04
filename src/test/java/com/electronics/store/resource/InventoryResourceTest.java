package com.electronics.store.resource;

import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.model.InventoryItem;
import com.electronics.store.request.InventoryItemRequest;
import com.electronics.store.service.inventory.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryResource.class)
@AutoConfigureMockMvc(addFilters = false)
class InventoryResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    private static final String INVENTORY_ID = "INV1234";
    private static final String PRODUCT_ID = "1234";
    private static final Integer PRODUCT_QUANTITY = 20;

    @Test
    void getInventoryDetailForProductPositive() throws Exception {
        // Arrange
        InventoryItem inventoryItem = InventoryItem.builder()
                .inventoryId(INVENTORY_ID).productQuantity(PRODUCT_QUANTITY)
                .productId(PRODUCT_ID).build();
        when(inventoryService.getInventoryDetailForProduct(anyString())).thenReturn(inventoryItem);

        mockMvc.perform(MockMvcRequestBuilders.get("/inventory/" + PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").isNotEmpty());

    }
    @Test
    void getInventoryDetailForProductNegative() throws Exception {
        // Arrange
        String fakeProductId = "FAKE_ID";
        when(inventoryService.getInventoryDetailForProduct(anyString())).thenThrow(ProductNotFoundException.class);

        // ACT - ASSERT
        Throwable exception = assertThrows(ProductNotFoundException.class, () -> inventoryService.getInventoryDetailForProduct(fakeProductId));
        assertNotNull(exception);
    }

    @Test
    void updateInventoryItemQuantity() throws Exception {
        InventoryItem inventoryItem = InventoryItem.builder()
                .inventoryId(INVENTORY_ID).productQuantity(PRODUCT_QUANTITY)
                .productId(PRODUCT_ID).build();

        InventoryItemRequest inventoryItemRequest = InventoryItemRequest.builder()
                .productId(PRODUCT_ID).productQuantity(PRODUCT_QUANTITY).build();
        String inventoryJson = new ObjectMapper().writeValueAsString(inventoryItemRequest);
        when(inventoryService.updateInventoryItemQuantity(any())).thenReturn(inventoryItem);


        mockMvc.perform(MockMvcRequestBuilders.put("/inventory")
                .content(inventoryJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryId").isNotEmpty());
    }

}