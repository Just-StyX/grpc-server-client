package com.jsl.service;

import com.jsl.api.grpc.v1.Product;
import com.jsl.api.grpc.v1.ProductId;
import com.jsl.api.grpc.v1.ProductInfoGrpc.*;
import com.jsl.repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends ProductInfoImplBase {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product, StreamObserver<ProductId> productIdStreamObserver) {
        ProductId productId = productRepository.addProduct(product);
        productIdStreamObserver.onNext(productId);
        productIdStreamObserver.onCompleted();
    }

    @Override
    public void getProduct(ProductId productId, StreamObserver<Product> productStreamObserver) {
        Product product = productRepository.getProduct(productId);
        productStreamObserver.onNext(product);
        productStreamObserver.onCompleted();
    }
}
