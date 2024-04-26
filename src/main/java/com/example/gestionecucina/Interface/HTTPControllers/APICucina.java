package com.example.gestionecucina.Interface.HTTPControllers;

import org.springframework.http.ResponseEntity;

/**
 * Interfaccia per comunicazioni HTTP al server REST GestioneCliente
 */
public interface APICucina {

    ResponseEntity getQueue();
    ResponseEntity selectOrder();
    ResponseEntity completeOrder();
}
