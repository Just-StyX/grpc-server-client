package com.jsl.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class GrpcServiceRunner implements CommandLineRunner {
    private final GrpcServer grpcServer;

    public GrpcServiceRunner(GrpcServer grpcServer) {
        this.grpcServer = grpcServer;
    }

    @Override
    public void run(String... args) throws Exception {
        grpcServer.start();
        grpcServer.block();
    }
}
