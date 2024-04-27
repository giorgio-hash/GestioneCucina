package com.example.gestionecucina.Domain.ports;

import com.example.gestionecucina.Domain.dto.CodaPostazioneDTO;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface FrontSignalPort {


    /**
     * Richiesta per ottenere una specifica coda di postazione in base all'identificativo ingrediente principale
     * @param ingredientePrincipale identificativo della codaPostazione <i>String<i/>
     * @return un oggetto container di tipo Optional che potrebbe contenere <i>codaPostazioneDTO<i/> oppure <i>null<i/>
     */
    Optional<CodaPostazioneDTO> getCodaPostazione(String ingredientePrincipale);

    /**
     * Richiesta per ottenere l'ordine che deve essere preparato in una specifica coda di postazione
     * in base all'identificativo ingrediente principale
     * @param ingredientePrincipale identificativo della codaPostazione <i>String<i/>
     * @return un oggetto container di tipo Optional che potrebbe contenere <i>codaPostazioneDTO<i/> oppure <i>null<i/>
     */
    Optional<OrdineDTO> getOrder(String ingredientePrincipale);

    /**
     * Notifica riguardo l'avvenuta preparazione di un ordine da parte di una determinata postazione della cucina
     * e invia la notifica sul rispettivo topic del message broker verso gestioneComanda
     *
     * @param ingredientePrincipale identificativo della postazione della cucina responsabile
     * @param notificaPrepOrdineDTO oggetto notifica di preparazione di un ordine
     * @return un oggetto container di tipo Optional che potrebbe contenere <i>OrdineDTO<i/>oppure<i>null<i/>
     */
    Optional<OrdineDTO> postNotifica(String ingredientePrincipale,
                                                  NotificaPrepOrdineDTO notificaPrepOrdineDTO) throws JsonProcessingException;


}
