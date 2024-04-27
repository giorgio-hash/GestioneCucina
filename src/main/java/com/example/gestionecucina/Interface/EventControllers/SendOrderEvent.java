package com.example.gestionecucina.Interface.EventControllers;

import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;

public interface SendOrderEvent {

    /**
     * Riceve un messaggio tramite Kafka dal servizio gestioneComanda in merito all'ordine di priorita' più elevata
     *
     * @param message il corpo del messaggio vero e proprio
     * @param topic topic del message broker sul quale si riceve il messaggio
     * @param partition numero di partizione sul quale si riceve il messaggio
     * @param offset numero di offset che presenta il messaggio ricevuto
     */
    @KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = "${spring.kafka.consumer.topic}")
    void receive(String message, String topic, Integer partition, Long offset) throws JsonProcessingException;

    /**
     * Restituisce l'ultima notifica letta dal listener
     *
     * @return oggetto notifica letto dal listener
     */
    OrdineDTO getLastMessageReceived();

}
