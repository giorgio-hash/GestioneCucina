package com.example.GestioneCucina.Domain;

import com.example.GestioneCucina.Domain.Entity.ClienteEntity;
import com.example.GestioneCucina.Domain.Entity.OrdineEntity;

public interface FrontSignalPort {
<<<<<<< Updated upstream
   void getQueue();
   void selectOrder();
   void completeOrder();
=======
    void getOrderData();
    void setOrderStatus();

    Iterable<ClienteEntity> getClienti();

    /**
     * Salva l'entita' ordine all'interno del database
     *
     * @param ordineEntity entità ordine da salvare
     * @return entita' ordine salvata
     */
    OrdineEntity save(OrdineEntity ordineEntity);
>>>>>>> Stashed changes
}
