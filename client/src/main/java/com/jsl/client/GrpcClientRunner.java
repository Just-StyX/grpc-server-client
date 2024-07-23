package com.jsl.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class GrpcClientRunner implements CommandLineRunner {
    @Autowired
    GrpcClient grpcClient;

    private static final Logger LOG = LoggerFactory.getLogger(GrpcClient.class);

    @Override
    public void run(String... args) throws Exception {
        grpcClient.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                grpcClient.shutdown();
            } catch (InterruptedException e) {
                LOG.error("Client stopped with error: {}", e.getMessage());
            }
        }));
    }
}
