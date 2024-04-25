package com.example.gestionecucina.Infrastructure.MessageBrokers;

import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.Domain.ports.MessagePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Classe che implementa la MessagePort e permette di scrivere un messaggio sul topic specificato del Message Broker
 */
@Service
@Log
public class PubOrdAdapter implements MessagePort<NotificaPrepOrdineDTO> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    /**
     * topic sul quale è in ascolto la cucina
     */
    @Value("${spring.kafka.producer.topic}")
    private String topic;

    @Autowired
    public PubOrdAdapter(final KafkaTemplate<String, String> kafkaTemplate, final ObjectMapper objectMapper){
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(NotificaPrepOrdineDTO notificaPrepOrdineDTO) throws JsonProcessingException {

        // Serializza in un oggetto JSON
        final String payload = objectMapper.writeValueAsString(notificaPrepOrdineDTO);

        // invia messaggio sul topic specificato
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, payload);
        future.whenComplete((result,ex)->{
            if(ex == null){
                log.info("Sent Message=[" + payload + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            else{
                log.info("Unable to send message=[" + payload + "] due to : " + ex.getMessage());
            }
        });
    }
}
