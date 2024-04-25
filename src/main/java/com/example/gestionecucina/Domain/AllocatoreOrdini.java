package com.example.gestionecucina.Domain;

import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.example.gestionecucina.Domain.ports.BackSignalPort;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log
public class AllocatoreOrdini implements BackSignalPort {

    @Value("${spring.kafka.consumer.topic}")
    private String topic;

    @Override
    public void notifyOrder(OrdineDTO orderDTO) {
        log.info("Received a notify from "
                + topic
                + " : { id: " + orderDTO.getId()
                + ", IdComanda: " + orderDTO.getIdComanda()
                + ", idPiatto: " + orderDTO.getIdPiatto()
                + ", stato: " + orderDTO.getStato()
                + ", tOrdinazione " + orderDTO.getTOrdinazione()
                + ", urgenzaCliente " + orderDTO.getUrgenzaCliente()
                + "}");
    }

}
