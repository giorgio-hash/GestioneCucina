package com.example.gestionecucina.Infrastructure.Repository;

import com.example.gestionecucina.Domain.DataPort;
import com.example.gestionecucina.Domain.Repository.IngredientePrincipaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JPADBAdapter implements DataPort {

    private final IngredientePrincipaleRepository iprep;

    @Autowired
    public JPADBAdapter(IngredientePrincipaleRepository iprep) {
        this.iprep = iprep;
    }

    @Override
    public List<String> getIdIngredienti() {
        return iprep
                .getAllIdIngredientePrincipale()
                .stream().map(String::toUpperCase)
                .toList();
    }
}
