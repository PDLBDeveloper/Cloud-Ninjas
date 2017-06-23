package de.pag.productregistryclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by denielhorvatic on 23.06.17.
 */
@RestController
@RequestMapping("/products")
public class RegistryApiGateway {

    private RestTemplate _restTemplate;

    @Autowired
    private Source _outputChannelSource;

    @Autowired
    public RegistryApiGateway(RestTemplate _restTemplate) {
        this._restTemplate = _restTemplate;
    }


    @RequestMapping(method = RequestMethod.POST)
    public void write(@RequestBody ProductDto product) {
        MessageChannel channel = this._outputChannelSource.output();
        channel.send(org.springframework.messaging.support.MessageBuilder.withPayload(
                product.getProductName()
                ).build()
        );

    }

    public Collection<String> fallbackNames() {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "fallbackNames")
    @RequestMapping(method = RequestMethod.GET, value = "/names")
    public Collection<String> getProductNames() {
        return this._restTemplate.exchange(
                "http://product-registry-service/products",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resources<ProductDto>>() {
                })
                .getBody()
                .getContent()
                .stream()
                .map(ProductDto::getProductName)
                .collect(Collectors.toList());
    }
}
