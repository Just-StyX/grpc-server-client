package com.jsl.server;

import com.jsl.service.ProductService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcServer {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Value("${grpc.port:8081}")
    private int port;
    private Server server;

    private final ProductService productService;

    public GrpcServer(ProductService productService) {
        this.productService = productService;
    }

    public void start() throws IOException, InterruptedException {
        LOG.info("gRPC server is starting on port: {}.", port);
        server = ServerBuilder.forPort(port)
                .addService(productService)
                .build().start();
        LOG.info("gRPC server started and listening on port: {}.", port);
        LOG.info("Following service are available: ");
        server.getServices()
                .forEach(s -> LOG.info("Service Name: {}", s.getServiceDescriptor().getName()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Shutting down gRPC server.");
            GrpcServer.this.stop();
            LOG.info("gRPC server shut down successfully.");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void block() throws InterruptedException {
        if (server != null) {
            // received the request until application is terminated
            server.awaitTermination();
        }
    }
}
