package com.example.gestionecucina.Domain.ports;

import com.example.gestionecucina.Domain.dto.OrdineDTO;

public interface BackSignalPort {
    /**
     * Permette al listener in ascolto sul topic di notificare l'arrivo di un ordine al sistema
     *
     * @param ordineDTO oggetto ordine inviato da parte di Gestione Comanda
     */
    void notifyOrder(OrdineDTO ordineDTO);
}
