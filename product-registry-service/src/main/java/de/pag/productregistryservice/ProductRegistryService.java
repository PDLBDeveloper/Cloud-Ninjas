package de.pag.productregistryservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by denielhorvatic on 23.06.17.
 */
@RepositoryRestResource
public interface ProductRegistryService extends JpaRepository<Product, Long> {
    //nobody kehrs about writing CRUD operations and create a rest service based on this
}
