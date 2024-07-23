package com.jsl.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.jsl.api.grpc.v1.Product;
import com.jsl.api.grpc.v1.ProductId;
import com.jsl.client.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ClientController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final GrpcClient grpcClient;

    public ClientController(GrpcClient grpcClient) {
        this.grpcClient = grpcClient;
    }

    @PostMapping
    public String addResource(@RequestParam String name, @RequestParam String description, @RequestParam float price) throws InvalidProtocolBufferException {
        Product product = Product.newBuilder().setDescription(description)
                .setPrice(price).setName(name).build();
        ProductId resp = grpcClient.getSourceServiceBlockingStub().addProduct(product);
        var printer = JsonFormat.printer().includingDefaultValueFields();
        LOG.info("Product Id received in Json Format: {}", resp);
        return printer.print(resp);
    }

    @GetMapping("/{productId}")
    public String getSources(@PathVariable String productId) throws InvalidProtocolBufferException {
        LOG.info("ProductId : {}", productId);
        ProductId request = ProductId.newBuilder().setValue(productId).build();
        Product response = grpcClient.getSourceServiceBlockingStub().getProduct(request);
        var printer = JsonFormat.printer().includingDefaultValueFields();
        LOG.info("Product received in Json Format: {}", response);
        return printer.print(response);
    }
}
