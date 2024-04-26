package com.example.gestionecucina.util;

import com.example.gestionecucina.Domain.Entity.CodaPostazioneEntity;
import com.example.gestionecucina.Domain.Entity.OrdineEntity;
import com.example.gestionecucina.Domain.dto.CodaPostazioneDTO;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;

/**
 * Oggetti utili nei test
 */
public class TestDataUtil {

    // ATTENZIONE: non modificare i valori !

    public static OrdineEntity createOrdineEntityA(){
        return OrdineEntity.builder()
                .idComanda(7)
                .idPiatto("RIS188")
                .stato(1)
                .urgenzaCliente(0)
                .build();
    }

    public static com.example.gestionecucina.Domain.dto.OrdineDTO createOrdineDtoA(){
        return com.example.gestionecucina.Domain.dto.OrdineDTO.builder()
                .idComanda(7)
                .idPiatto("RIS188")
                .stato(1)
                .urgenzaCliente(0)
                .build();
    }

    public static OrdineEntity createOrdineEntityB(){
        return OrdineEntity.builder()
                .id(16)
                .idComanda(7)
                .idPiatto("SPA279")
                .stato(1)
                .urgenzaCliente(1)
                .build();
    }

    public static com.example.gestionecucina.Domain.dto.OrdineDTO createOrdineDtoB(){
        return com.example.gestionecucina.Domain.dto.OrdineDTO.builder()
                .id(16)
                .idComanda(7)
                .idPiatto("SPA279")
                .stato(1)
                .urgenzaCliente(1)
                .build();
    }

    public static OrdineEntity createOrdineEntityC(){
        return OrdineEntity.builder()
                .id(55)
                .idComanda(37)
                .idPiatto("PES789")
                .stato(0)
                .tOrdinazione(new java.sql.Timestamp(System.currentTimeMillis()))
                .urgenzaCliente(0)
                .build();
    }

    public static com.example.gestionecucina.Domain.dto.OrdineDTO createOrdineDtoC(){
        return com.example.gestionecucina.Domain.dto.OrdineDTO.builder()
                .id(55)
                .idComanda(37)
                .idPiatto("PES789")
                .stato(0)
                .tOrdinazione(new java.sql.Timestamp(System.currentTimeMillis()))
                .urgenzaCliente(0)
                .build();
    }

    public static NotificaPrepOrdineDTO createotificaPrepOrdineDTOA(){
        return NotificaPrepOrdineDTO.builder()
                .id(1)
                .idComanda(7)
                .build();
    }

    public static NotificaPrepOrdineDTO createotificaPrepOrdineDTOB(){
        return NotificaPrepOrdineDTO.builder()
                .id(2)
                .idComanda(4)
                .build();
    }

    public static CodaPostazioneEntity createCodaPostazioneEntityA(){
        CodaPostazioneEntity codaPostazioneEntity = new CodaPostazioneEntity("RISO");
        codaPostazioneEntity.insert(createOrdineEntityA());
        codaPostazioneEntity.insert(createOrdineEntityB());
        codaPostazioneEntity.insert(createOrdineEntityC());
        return codaPostazioneEntity;
    }

    public static CodaPostazioneDTO createCodaPostazioneDTOA(){
        CodaPostazioneDTO codaPostazioneDTO = new CodaPostazioneDTO("RISO");
        codaPostazioneDTO.insert(createOrdineDtoA());
        codaPostazioneDTO.insert(createOrdineDtoB());
        codaPostazioneDTO.insert(createOrdineDtoC());
        return codaPostazioneDTO;
    }


}
