package de.pag.productregistryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@EnableBinding(Sink.class)
@EnableEurekaClient
@SpringBootApplication
public class ProductRegistryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductRegistryServiceApplication.class, args);
    }

}

@Component
class DummyCommandLinerRunner implements CommandLineRunner {

    private ProductRegistryService _repository;

    @Autowired
    public DummyCommandLinerRunner(ProductRegistryService _repository) {
        this._repository = _repository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create inital product entities
        Stream.of("CarVision", "Blockchain", "CleaningService", "Carpass","CarCertification", "MileageApproval")
                .forEach(p -> _repository.save(new Product(p)));

        // print them all out (for test reason)
        _repository.findAll().forEach(p -> System.out.println(p));
    }
}


