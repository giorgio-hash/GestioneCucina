package com.example.gestionecucina.Domain.services;

import com.example.gestionecucina.Domain.Entity.OrdineEntity;

public interface OrdineService {

    /**
     * Salva l'entita' ordine all'interno del database
     *
     * @param ordineEntity entità ordine da salvare
     * @return entita' ordine salvata
     */
    OrdineEntity save(OrdineEntity ordineEntity);

}
