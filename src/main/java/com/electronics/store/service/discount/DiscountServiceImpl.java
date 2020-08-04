package com.electronics.store.service.discount;

import com.electronics.store.data.DiscountRepository;
import com.electronics.store.data.ProductRepository;
import com.electronics.store.domainvalue.DiscountType;
import com.electronics.store.exception.InvalidDiscountTypeException;
import com.electronics.store.exception.NoDiscountFoundForProduct;
import com.electronics.store.exception.NoDiscountWithCriteriaException;
import com.electronics.store.exception.NoSuchProductInStore;
import com.electronics.store.model.Product;
import com.electronics.store.model.ProductDiscount;
import com.electronics.store.model.Discount;
import com.electronics.store.request.CreateDiscountRequest;
import com.electronics.store.request.UpdateDiscountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public ProductDiscount createNewDiscount(CreateDiscountRequest createDiscountRequest) throws NoSuchProductInStore {
        ProductDiscount productDiscount = discountRepository.findByProductId(createDiscountRequest.getProductId()).orElseGet(ProductDiscount::new);
        List<Discount> discountList = productDiscount.getDiscounts();
        Discount discount = Discount.builder()
                .discountType(createDiscountRequest.getDiscountType())
                .dateCreated(ZonedDateTime.now())
                .dateExpired(createDiscountRequest.getDateExpired())
                .minimumQty(createDiscountRequest.getMinimumQty())
                .build();
        if(createDiscountRequest.getDiscountBundleProduct()!=null && !"".equals(createDiscountRequest.getDiscountBundleProduct())){
            Product bundleProduct = productRepository.findById(createDiscountRequest.getDiscountBundleProduct()).orElseThrow(()-> new NoSuchProductInStore("The Bundled Product with Product Id " + createDiscountRequest.getDiscountBundleProduct()+" not found"));
            discount.setDiscountBundleProduct(bundleProduct);
        }
        if(createDiscountRequest.getDiscountPercent() != null){
            discount.setDiscountPercent(createDiscountRequest.getDiscountPercent());
        }
        if(CollectionUtils.isEmpty(productDiscount.getDiscounts())){
            discountList = new ArrayList<>();
        }
        discountList.add(discount);
        productDiscount.setProductId(createDiscountRequest.getProductId());
        productDiscount.setDiscounts(discountList);
        return discountRepository.save(productDiscount);
    }

    @Override
    public List<ProductDiscount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public ProductDiscount getDiscountForProduct(String productId) {
        return discountRepository.findByProductId(productId).orElseGet(ProductDiscount::new);
    }

    @Override
    public ProductDiscount updateDiscountRequest(UpdateDiscountRequest updateDiscountRequest) throws NoDiscountFoundForProduct, NoDiscountWithCriteriaException, InvalidDiscountTypeException, NoSuchProductInStore {
        ProductDiscount productDiscount = discountRepository.findByProductId(updateDiscountRequest.getProductId()).orElseThrow(()-> new NoDiscountFoundForProduct("There is no discount on the product with product Id "+ updateDiscountRequest.getProductId()));
        List<Discount> discountList = productDiscount.getDiscounts();
        Discount discount = discountList.stream().filter(l -> updateDiscountRequest.getDiscountId().equals(l.getDiscountId())).findAny().orElseThrow(()-> new NoDiscountWithCriteriaException("There is no discount for product Id"+ updateDiscountRequest.getProductId()+" with criteria Id "+ updateDiscountRequest.getDiscountId()));
        discountList.remove(discount);
        if(discount.getDiscountType().equals(updateDiscountRequest.getDiscountType())) {
            if (DiscountType.PERCENTAGE.equals(discount.getDiscountType())) {
                discount.setDiscountPercent(updateDiscountRequest.getDiscountPercent());
            } else {
                Product bundleProduct = productRepository.findById(updateDiscountRequest.getDiscountBundleProduct()).orElseThrow(()-> new NoSuchProductInStore("The Bundled Product with Product Id " + updateDiscountRequest.getDiscountBundleProduct()+" not found"));
                discount.setDiscountBundleProduct(bundleProduct);
            }
            discount.setDateExpired(updateDiscountRequest.getDateExpired());
        }else{
            throw new InvalidDiscountTypeException("The Discount Type is " + discount.getDiscountType() + " You have entered " + updateDiscountRequest.getDiscountType()+ " type");
        }
        discountList.add(discount);
        productDiscount.setDiscounts(discountList);
        return discountRepository.save(productDiscount);
    }

    @Override
    public Boolean deleteDiscount(String discountId) throws NoDiscountFoundForProduct {
        ProductDiscount productDiscount = discountRepository.findById(discountId).orElseThrow(()-> new NoDiscountFoundForProduct("There is no discount with discountId  "+discountId));
        discountRepository.delete(productDiscount);
        return true;
    }
}
