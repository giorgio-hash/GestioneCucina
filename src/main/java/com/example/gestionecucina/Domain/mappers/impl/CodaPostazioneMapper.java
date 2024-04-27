package com.example.gestionecucina.Domain.mappers.impl;

import com.example.gestionecucina.Domain.Entity.CodaPostazioneEntity;
import com.example.gestionecucina.Domain.Entity.OrdineEntity;
import com.example.gestionecucina.Domain.dto.CodaPostazioneDTO;
import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.example.gestionecucina.Domain.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Implementazione del Mapper tra Entita' a DTO e viceversa
 */
@Component
public class CodaPostazioneMapper implements Mapper<CodaPostazioneEntity, CodaPostazioneDTO> {

    private OrdineMapper ordineMapper;
    public CodaPostazioneMapper(OrdineMapper ordineMapper) {
        this.ordineMapper = ordineMapper;
    }

    @Override
    public CodaPostazioneDTO mapTo(CodaPostazioneEntity codaPostazioneEntity) {

        CodaPostazioneDTO codaPostazioneDTO = new CodaPostazioneDTO(codaPostazioneEntity.getIngredientePrincipale());
        codaPostazioneDTO.setNumeroOrdiniPresenti(codaPostazioneEntity.getNumeroOrdiniPresenti());

        Queue<OrdineDTO> queueDTO = codaPostazioneEntity.getQueue().stream()
                .map(ordineMapper::mapTo)
                .collect(Collectors.toCollection(LinkedList::new));
        codaPostazioneDTO.setQueue(queueDTO);

        return codaPostazioneDTO;
    }

    @Override
    public CodaPostazioneEntity mapFrom(CodaPostazioneDTO codaPostazioneDTO) {

        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity(codaPostazioneDTO.getIngredientePrincipale());
        codaPostazioneEntity.setNumeroOrdiniPresenti(codaPostazioneDTO.getNumeroOrdiniPresenti());

        Queue<OrdineEntity> queueEntity = codaPostazioneDTO.getQueue().stream()
                .map(ordineMapper::mapFrom)
                .collect(Collectors.toCollection(LinkedList::new));
        codaPostazioneEntity.setQueue(queueEntity);

        return codaPostazioneEntity;
    }
}
