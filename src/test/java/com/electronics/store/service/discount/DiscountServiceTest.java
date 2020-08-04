package com.electronics.store.service.discount;

import com.electronics.store.domainvalue.DiscountType;
import com.electronics.store.exception.InvalidDiscountTypeException;
import com.electronics.store.exception.NoDiscountFoundForProduct;
import com.electronics.store.exception.NoDiscountWithCriteriaException;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.model.Discount;
import com.electronics.store.model.Product;
import com.electronics.store.model.ProductDiscount;
import com.electronics.store.request.CreateDiscountRequest;
import com.electronics.store.request.CreateProductRequest;
import com.electronics.store.request.UpdateDiscountRequest;
import com.electronics.store.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiscountServiceTest {
    private static final String DUMMY_DESCRIPTION = "This is a dummy product used for Unit tests ";
    private static final Double DUMMY_PRICE = 20.0;
    private static final String DUMMY_PRODUCT = "Dummy Product";
    private static final ZonedDateTime dateExpired = ZonedDateTime.parse("2020-09-09T10:15:30+08:00");
    private static final Integer MINIMUM_QTY = 2;
    private static final Integer DISCOUNT_PERCENTAGE =20 ;


    @Autowired
    DiscountService discountService;
    @Autowired
    ProductService productService;


    @Test
    void createNewDiscount() throws NoSuchProductInStore {

        ProductDiscount productDiscount = createProductDiscount();
        ProductDiscount resProductDiscount = discountService.getDiscountForProduct(productDiscount.getProductId());
        assertNotNull(resProductDiscount);
        assertEquals(resProductDiscount.getProductDiscountId(),productDiscount.getProductDiscountId());
        assertEquals(resProductDiscount.getProductId(),productDiscount.getProductId());
    }

    @Test
    void getAllDiscounts() throws NoSuchProductInStore {
        ProductDiscount productDiscount = createProductDiscount();
        List<ProductDiscount> productDiscounts = discountService.getAllDiscounts();
        assertNotNull(productDiscounts);
    }

    @Test
    void getDiscountForProduct() throws NoSuchProductInStore {
        ProductDiscount productDiscount = createProductDiscount();
        ProductDiscount resultProductDiscount = discountService.getDiscountForProduct(productDiscount.getProductId());
        assertNotNull(resultProductDiscount);
        assertEquals(resultProductDiscount.getProductId(),productDiscount.getProductId());
        assertEquals(resultProductDiscount.getProductDiscountId(),productDiscount.getProductDiscountId());
    }

    @Test
    @Transactional
    void updateDiscountRequest() throws NoSuchProductInStore, NoDiscountWithCriteriaException, InvalidDiscountTypeException, NoDiscountFoundForProduct {
        ProductDiscount productDiscount = createProductDiscount();
        Discount discount = productDiscount.getDiscounts().get(0);
        Integer newDiscountPercent = 10;
        UpdateDiscountRequest updateDiscountRequest = UpdateDiscountRequest.builder()
                .discountId(discount.getDiscountId()).productId(productDiscount.getProductId())
                .discountType(discount.getDiscountType()).discountPercent(newDiscountPercent).build();
        ProductDiscount resultProductDiscount = discountService.updateDiscountRequest(updateDiscountRequest);
        Discount resultDiscount = resultProductDiscount.getDiscounts().get(0);
        assertNotNull(resultProductDiscount);
        assertEquals(productDiscount.getProductDiscountId(),resultProductDiscount.getProductDiscountId());
        assertEquals(discount.getDiscountId(),resultDiscount.getDiscountId());
        assertEquals(discount.getDiscountPercent(),resultDiscount.getDiscountPercent());
        assertEquals(discount.getDiscountPercent(),newDiscountPercent);
    }

    @Test
    void deleteDiscount() throws NoSuchProductInStore, NoDiscountFoundForProduct {
        ProductDiscount productDiscount = createProductDiscount();
        Boolean isDeleted = discountService.deleteDiscount(productDiscount.getProductDiscountId());
        assertEquals(Boolean.TRUE,isDeleted);
    }

    // Data preparation

    private Product createProduct(){
        CreateProductRequest createProductRequest = CreateProductRequest.builder().productName(DUMMY_PRODUCT)
                .productDescription(DUMMY_DESCRIPTION).productPrice(DUMMY_PRICE).build();
        return productService.create(createProductRequest);
    }
    ProductDiscount createProductDiscount() throws NoSuchProductInStore {
        Product product = createProduct();
        CreateDiscountRequest createDiscountRequest = CreateDiscountRequest.builder()
                .productId(product.getProductId()).minimumQty(MINIMUM_QTY).discountPercent(DISCOUNT_PERCENTAGE)
                .discountType(DiscountType.PERCENTAGE).dateExpired(dateExpired).build();
        return discountService.createNewDiscount(createDiscountRequest);
    }
}