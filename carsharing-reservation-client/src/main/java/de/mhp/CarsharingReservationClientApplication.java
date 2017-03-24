package de.mhp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class CarsharingReservationClientApplication {

    @Bean
    @LoadBalanced
    RestTemplate _restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(CarsharingReservationClientApplication.class, args);
    }
}

@RestController
@RequestMapping("/reservations")
class ReservationApiGatewayRestController {


    private RestTemplate _restTemplate;

    @Autowired
    public ReservationApiGatewayRestController(RestTemplate _restTemplate) {
        this._restTemplate = _restTemplate;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/names")
    public Collection<String> names() {
        return this._restTemplate.exchange("http://carsharing-reservation-service/reservations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Resources<Reservation>>() {
                }
        )
                .getBody()
                .getContent()
                .stream()
                .map(Reservation::getReservationName)
                .collect(Collectors.toList());
    }
}

class Reservation {
    private String reservationName;

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }
}