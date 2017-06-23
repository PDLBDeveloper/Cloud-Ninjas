package de.pag.productregistryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * Created by denielhorvatic on 23.06.17.
 */
@MessageEndpoint
public class ProductProcessor {

    private final ProductRegistryService _productRegistryService;

    @Autowired
    public ProductProcessor(ProductRegistryService _productRegistryService) {
        this._productRegistryService = _productRegistryService;
    }

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void acceptNewProducts(String productName) {
        this._productRegistryService.save(new Product(productName));
    }
}
