package com.example.gestionecucina.Domain;

import com.example.gestionecucina.Domain.Entity.CodaPostazioneEntity;
import com.example.gestionecucina.Domain.Entity.OrdineEntity;
import com.example.gestionecucina.Domain.dto.CodaPostazioneDTO;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.example.gestionecucina.Domain.mappers.impl.CodaPostazioneMapper;
import com.example.gestionecucina.Domain.mappers.impl.OrdineMapper;
import com.example.gestionecucina.Domain.ports.FrontSignalPort;
import com.example.gestionecucina.Domain.ports.MessagePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class GestioneCode implements FrontSignalPort {

    @Getter
    @Setter
    private HashMap<String, CodaPostazioneEntity> postazioni;
    private OrdineMapper ordineMapper;
    private CodaPostazioneMapper codaPostazioneMapper;
    private MessagePort<NotificaPrepOrdineDTO> messagePort;

    @Autowired
    public GestioneCode(CodaPostazioneMapper codaPostazioneMapper,
                        OrdineMapper ordineMapper,
                        MessagePort<NotificaPrepOrdineDTO> messagePort) {
        this.codaPostazioneMapper = codaPostazioneMapper;
        this.ordineMapper = ordineMapper;
        this.messagePort = messagePort;
        this.postazioni = new HashMap<>();
    }

    @Override
    public Optional<CodaPostazioneDTO> getCodaPostazione(String ingredientePrincipale) {
        Optional<CodaPostazioneEntity> codaPostazioneEntity = Optional.ofNullable(postazioni.get(ingredientePrincipale.toUpperCase()));
        if (!codaPostazioneEntity.isPresent()) {
            return Optional.empty();
        }
        CodaPostazioneDTO codaPostazioneDTO = codaPostazioneMapper.mapTo(codaPostazioneEntity.get());
        return Optional.ofNullable(codaPostazioneDTO);
    }

    @Override
    public Optional<OrdineDTO> getOrder(String ingredientePrincipale) {
        Optional<CodaPostazioneEntity> codaPostazioneEntity = Optional.ofNullable(postazioni.get(ingredientePrincipale.toUpperCase()));
        if(codaPostazioneEntity.isPresent()) {
            Optional<OrdineEntity> ordineEntity = codaPostazioneEntity.get().element();
            // TODO: settare stato di ordine su: in preparazione
            if (ordineEntity.isPresent()) {
                OrdineDTO ordineDTO = ordineMapper.mapTo(ordineEntity.get());
                return Optional.ofNullable(ordineDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrdineDTO> postNotifica(String ingredientePrincipale,
                                            NotificaPrepOrdineDTO notificaPrepOrdineDTO) throws JsonProcessingException {
        Optional<CodaPostazioneEntity> codaPostazioneEntity = Optional.ofNullable(postazioni.get(ingredientePrincipale.toUpperCase()));
        if(codaPostazioneEntity.isPresent()) {
            Optional<OrdineEntity> ordineEntity = codaPostazioneEntity.get().remove();
            messagePort.send(notificaPrepOrdineDTO);
            if (ordineEntity.isPresent()) {
                OrdineDTO ordineDTO = ordineMapper.mapTo(ordineEntity.get());
                return Optional.ofNullable(ordineDTO);
            }
        }
        return Optional.empty();
    }

}
