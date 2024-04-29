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
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.modelmapper.internal.util.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class GestioneCode implements FrontSignalPort, CodeIF {

    @Getter
    @Setter
    private HashMap<String, CodaPostazioneEntity> postazioni;
    private OrdineMapper ordineMapper;
    private CodaPostazioneMapper codaPostazioneMapper;
    private MessagePort<NotificaPrepOrdineDTO> messagePort;
    private DataPort dataPort;
    @Value("${spring.application.GestioneCode.init.postconstruct}")
    private boolean initPostConstruct;
    @Autowired
    public GestioneCode(CodaPostazioneMapper codaPostazioneMapper,
                        OrdineMapper ordineMapper,
                        MessagePort<NotificaPrepOrdineDTO> messagePort, DataPort dataPort) {
        this.codaPostazioneMapper = codaPostazioneMapper;
        this.ordineMapper = ordineMapper;
        this.messagePort = messagePort;
        this.dataPort = dataPort;
        this.postazioni = new HashMap<>();
    }

    /**
     * permette di popolare l’HashMap automaticamente quando l’applicazione viene avviata.
     */
    @PostConstruct
    public void init() {
        if (!initPostConstruct) {

            Iterable<String> exsisting = dataPort.getIdIngredienti();

            if(Iterables.getLength(exsisting) == 0) throw new RuntimeException("Database vuoto?");

            for (String s : exsisting)
                postazioni.put( s , new CodaPostazioneEntity(s));

            return;
        }

        CodaPostazioneEntity codaPostazioneEntityA = new CodaPostazioneEntity("RISO");
        CodaPostazioneEntity codaPostazioneEntityB = new CodaPostazioneEntity("PASTA");
        CodaPostazioneEntity codaPostazioneEntityC = new CodaPostazioneEntity("CARNE");
        CodaPostazioneEntity codaPostazioneEntityD = new CodaPostazioneEntity("PESCE");

        codaPostazioneEntityA.insert(new OrdineEntity(1,7,"RIS001",1,new java.sql.Timestamp(System.currentTimeMillis()),2));
        codaPostazioneEntityA.insert(new OrdineEntity(5,1,"RIS015",1,new java.sql.Timestamp(System.currentTimeMillis()),0));
        codaPostazioneEntityA.insert(new OrdineEntity(8,3,"RIS152",1,new java.sql.Timestamp(System.currentTimeMillis()),1));

        codaPostazioneEntityB.insert(new OrdineEntity(4,2,"PAS780",1,new java.sql.Timestamp(System.currentTimeMillis()),1));
        codaPostazioneEntityB.insert(new OrdineEntity(7,7,"PAS450",1,new java.sql.Timestamp(System.currentTimeMillis()),0));

        codaPostazioneEntityC.insert(new OrdineEntity(2,4,"CAR580",1,new java.sql.Timestamp(System.currentTimeMillis()),1));
        codaPostazioneEntityC.insert(new OrdineEntity(3,5,"CAR789",1,new java.sql.Timestamp(System.currentTimeMillis()),0));
        codaPostazioneEntityC.insert(new OrdineEntity(9,1,"CAR123",1,new java.sql.Timestamp(System.currentTimeMillis()),2));
        codaPostazioneEntityC.insert(new OrdineEntity(11,2,"CAR789",1,new java.sql.Timestamp(System.currentTimeMillis()),0));
        codaPostazioneEntityC.insert(new OrdineEntity(12,3,"CAR123",1,new java.sql.Timestamp(System.currentTimeMillis()),1));

        postazioni.put("RISO", codaPostazioneEntityA);
        postazioni.put("PASTA", codaPostazioneEntityB);
        postazioni.put("CARNE", codaPostazioneEntityC);
        postazioni.put("PESCE", codaPostazioneEntityD);
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
        //TODO: Controllare che la notifica si riferisca effettivamente all'ordine considerato
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

    @Override
    public void push(OrdineDTO dto) throws RuntimeException{

        Optional<OrdineEntity> o_opt = Optional.ofNullable(ordineMapper.mapFrom(dto));
        if(o_opt.isEmpty()) throw new RuntimeException("Non è possibile mappare OrdineDTO ( "+dto+" ) a OrdineEntity");
        OrdineEntity o = o_opt.get();

        //evaluation dell'ingrediente principale attraverso idiatto per ottenere le code associate
        String cod_ingr = o.getIdPiatto().replaceAll("[0-9]", "");
        List<String> chiavi_code = postazioni.keySet().stream()
                .filter(key -> key.startsWith(cod_ingr))
                .toList();
        //verifica esistenza code
        if(chiavi_code.isEmpty()) throw new RuntimeException("coda non trovata per cod_ingr: "+ cod_ingr);
        //selezione coda
        if(chiavi_code.size() == 1){
            log.info("Aggiunto OrdineEntity a coda: "+ chiavi_code.get(0) + "...");
            CodaPostazioneEntity coda_selezionata = postazioni.get(chiavi_code.get(0));
            coda_selezionata.insert(o);
            log.info("Coda Aggiornata: " + postazioni.get(chiavi_code.get(0)));
        }
        else {
            //TODO per future fasi
            log.info("Trovate più code: "+ chiavi_code);
        }

    }
}
