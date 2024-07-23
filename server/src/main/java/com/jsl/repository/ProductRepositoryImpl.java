package com.jsl.repository;

import com.jsl.api.grpc.v1.Product;
import com.jsl.api.grpc.v1.ProductId;
import com.jsl.entity.ProductEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    private final ProductEntityRepository productEntityRepository;

    public ProductRepositoryImpl(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }

    @Override
    public ProductId addProduct(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setDescription(product.getDescription());
        productEntity.setName(product.getName());
        productEntity.setPrice(BigDecimal.valueOf(product.getPrice()));
        var saveProductEntity = productEntityRepository.save(productEntity);
        return ProductId.newBuilder().setValue(saveProductEntity.getId()).build();
    }

    @Override
    public Product getProduct(ProductId productId) {
        var foundProductEntity = productEntityRepository.findById(productId.getValue());
        if (foundProductEntity.isPresent()) {
            var productEntity = foundProductEntity.get();
            return Product.newBuilder()
                    .setDescription(productEntity.getDescription())
                    .setName(productEntity.getName())
                    .setPrice(productEntity.getPrice().floatValue())
                    .setId(productEntity.getId()).build();
        }
        return null;
    }
}
