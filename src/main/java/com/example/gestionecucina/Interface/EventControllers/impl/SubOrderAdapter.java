package com.example.gestionecucina.Interface.EventControllers.impl;

import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.example.gestionecucina.Domain.ports.BackSignalPort;
import com.example.gestionecucina.Interface.EventControllers.SendOrderEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

// TODO: Usare un OrderEvent al posto di OrdineDTO !

@Component
public class SubOrderAdapter implements SendOrderEvent {

    private ObjectMapper objectMapper;
    private final BackSignalPort backSignalPort;

    /**
     * variabile thread safe che serve per fini di test per verificare che il listener abbia ricevuto un messaggio
     */
    private CountDownLatch latch = new CountDownLatch(1);
    private final Logger logger = LoggerFactory.getLogger(SubOrderAdapter.class);
    private OrdineDTO lastMessageReceived;

    @Autowired
    public SubOrderAdapter(BackSignalPort backSignalPort, ObjectMapper objectMapper) {
        this.backSignalPort = backSignalPort;
        this.objectMapper = objectMapper;
    }

    @Override
    public void receive(@Payload String message,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) throws JsonProcessingException {
        logger.info("Received a message {}, from {} topic, " +
                "{} partition, and {} offset", message.toString(), topic, partition, offset);
        OrdineDTO ordineDTO = objectMapper.readValue(message, OrdineDTO.class);
        backSignalPort.notifyOrder(ordineDTO);
        lastMessageReceived = ordineDTO;
        latch.countDown();
    }

    /**
     * resetta il valore del latch
     */
    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    /**
     * restituisce il latch
     *
     * @return latch: variabile thread safe che serve per fini di test per verificare
     * che il listener abbia ricevuto un messaggio
     */
    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public OrdineDTO getLastMessageReceived() {
        return lastMessageReceived;
    }
}
