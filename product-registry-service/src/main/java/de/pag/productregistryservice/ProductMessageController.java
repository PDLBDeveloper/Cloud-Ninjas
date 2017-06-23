package de.pag.productregistryservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by denielhorvatic on 23.06.17.
 */
@RestController
@RefreshScope
public class ProductMessageController {

    @Value("${message}")
    private String message;

    @RequestMapping("/message")
    public String read(){
        return this.message;
    }
}
