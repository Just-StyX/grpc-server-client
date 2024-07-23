package com.jsl.repository;

import com.jsl.api.grpc.v1.Product;
import com.jsl.api.grpc.v1.ProductId;

public interface ProductRepository {
    ProductId addProduct(Product product);
    Product getProduct(ProductId productId);
}
