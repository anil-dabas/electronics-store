package com.electronics.store.resource;

import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.mapper.InventoryMapper;
import com.electronics.store.mapper.ProductMapper;
import com.electronics.store.model.InventoryItemDTO;
import com.electronics.store.model.ProductDTO;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.InventoryItemRequest;
import com.electronics.store.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryResource {

    private final InventoryService inventoryService;

    InventoryResource(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }


    @GetMapping
    public ResponseEntity<List<InventoryItemDTO>> getAllInventoryItems(){
       return new ResponseEntity<>(InventoryMapper.convertToInventoryItemDTOs(inventoryService.getAllInventoryItems()), HttpStatus.OK);
    }
    @GetMapping("/{product_Id}")
    public ResponseEntity<InventoryItemDTO> getInventoryDetailForProduct(@PathVariable(value = "product_Id")String productId) throws ProductNotFoundException {
        return new ResponseEntity<>(InventoryMapper.convertToInventoryItemDTO(inventoryService.getInventoryDetailForProduct(productId)), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<InventoryItemDTO> createInventoryItem(@RequestBody InventoryItemRequest inventoryItemRequest){
        return  new ResponseEntity<>(InventoryMapper.convertToInventoryItemDTO(inventoryService.createInventoryItem(inventoryItemRequest)),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<InventoryItemDTO> updateInventoryItemQuantity(@RequestBody InventoryItemRequest inventoryItemRequest) throws ProductNotFoundException {
        return  new ResponseEntity<>(InventoryMapper.convertToInventoryItemDTO(inventoryService.updateInventoryItemQuantity(inventoryItemRequest)),HttpStatus.OK);
    }
    @DeleteMapping("/{product_Id}")
    public ResponseEntity<Boolean> deleteInventoryItem(@PathVariable(value = "product_Id") String productId) throws NoSuchProductInStore {
        return  new ResponseEntity<>(inventoryService.deleteInventoryItem(productId),HttpStatus.OK);
    }
}
