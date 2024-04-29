package com.example.gestionecucina.Domain.Repository;


import com.example.gestionecucina.Domain.Entity.IngredientePrincipaleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientePrincipaleRepository extends CrudRepository<IngredientePrincipaleEntity, String> {

    @Query("SELECT i.id FROM IngredientePrincipaleEntity i")
    List<String> getAllIdIngredientePrincipale();

}
