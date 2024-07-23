package com.jsl.client;

import com.jsl.api.grpc.v1.ProductInfoGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class GrpcClient {
    private static final Logger LOG = LoggerFactory.getLogger(GrpcClient.class);

    @Value("${grpc.server.host:localhost}")
    private String host;
    @Value("${grpc.server.port:8080}")
    private int port;

    private ManagedChannel managedChannel;
    private ProductInfoGrpc.ProductInfoBlockingStub productInfoBlockingStub;

    public void start() {
        managedChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        productInfoBlockingStub = ProductInfoGrpc.newBlockingStub(managedChannel);

        LOG.info("gRPC client connected to {}:{}", host, port);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);

        LOG.info("gRPC client disconnected successfully.");
    }

    public ProductInfoGrpc.ProductInfoBlockingStub getSourceServiceBlockingStub() {
        return this.productInfoBlockingStub;
    }
}
