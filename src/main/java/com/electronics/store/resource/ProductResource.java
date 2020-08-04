package com.electronics.store.resource;

import com.electronics.store.exception.ProductNotFoundException;
import com.electronics.store.mapper.ProductMapper;
import com.electronics.store.model.ProductDTO;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.PatchProductDescriptionRequest;
import com.electronics.store.request.PatchProductPriceRequest;
import com.electronics.store.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductResource {

    private final ProductService productService;
    // Constructor DI
    @Autowired
    ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct( @RequestBody CreateProductRequest createProductRequest){
        return new ResponseEntity<>(ProductMapper.convertToProductDTO(productService.create(createProductRequest)),HttpStatus.CREATED);
    }
    @GetMapping
    public  ResponseEntity<List<ProductDTO>> getProducts(){
        return new ResponseEntity<>(ProductMapper.convertToProductDTOList(productService.getAllProducts()), HttpStatus.OK);
    }
    @GetMapping(value = "/{product_Id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(value = "product_Id") String productId) throws ProductNotFoundException {
        return new ResponseEntity<>(ProductMapper.convertToProductDTO(productService.getProductById(productId)), HttpStatus.OK);
    }
    @PatchMapping(value = "/{product_Id}/description")
    public  ResponseEntity<Boolean> patchProductDescription(@PathVariable(value = "product_Id") String productId,@RequestBody() PatchProductDescriptionRequest patchRequest) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.changeProductDescription(productId,patchRequest),HttpStatus.OK);
    }
    @PatchMapping(value = "/{product_Id}/price")
    public  ResponseEntity<Boolean> patchProductPrice(@PathVariable(value = "product_Id") String productId,@RequestBody() PatchProductPriceRequest patchRequest) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.changeProductPrice(productId,patchRequest),HttpStatus.OK);
    }
    @DeleteMapping(value = "/{product_Id}")
    public void deleteProductById(@PathVariable(value = "product_Id") String productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
    }

}
