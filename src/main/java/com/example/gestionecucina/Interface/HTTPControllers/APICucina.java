package com.example.gestionecucina.Interface.HTTPControllers;

import com.example.gestionecucina.Domain.dto.CodaPostazioneDTO;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Interfaccia per comunicazioni HTTP al server REST GestioneCliente
 */
@RequestMapping(path = "/cucina")
public interface APICucina {

    /**
     * Espone la coda della postazione corrispondente alll'identificativo di ingrediente principale specificato
     *
     * @return entità risposta che contiene l'oggetto richiesto e una risposta HTTP associata
     */
    @GetMapping(path = "/codapostazione/{ingredienteprincipale}")
    ResponseEntity<CodaPostazioneDTO> getQueue(@PathVariable String ingredienteprincipale);

    /**
     * permette di ricevere l'ordere che deve eseere preparato per una determinata postazione della cucina
     *
     * @param ingredienteprincipale identificativo della postazione della cucina responsabile
     * @return entità risposta che contiene l'oggetto richiesto e una risposta HTTP associata
     */
    @GetMapping(path = "/codapostazione/{ingredienteprincipale}/nextorder")
    ResponseEntity<OrdineDTO> selectOrder(@PathVariable String ingredienteprincipale);

    /**
     * permette di notificare l'avvenuta preparazione di un ordine da parte di una determinata postazione della cucina
     *
     * @param ingredienteprincipale identificativo della postazione della cucina responsabile
     * @param notificaPrepOrdineDTO oggetto notifica di preparazione di un ordine
     * @return entità risposta che contiene l'oggetto richiesto e una risposta HTTP associata
     */
    @PostMapping(path = "/codapostazione/{ingredienteprincipale}")
    ResponseEntity<OrdineDTO> completeOrder(@PathVariable String ingredienteprincipale,
                                                        @RequestBody NotificaPrepOrdineDTO notificaPrepOrdineDTO) throws JsonProcessingException;
}
