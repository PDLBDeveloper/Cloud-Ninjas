package de.pag.productregistryservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by denielhorvatic on 23.06.17.
 */
@Entity
public class Product {

    @GeneratedValue @Id
    private Long id;
    private String productName;

    public Product() {
    }

    public Product(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                '}';
    }
}


