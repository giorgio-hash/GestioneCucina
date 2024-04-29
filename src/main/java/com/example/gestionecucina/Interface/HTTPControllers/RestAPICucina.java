package com.example.gestionecucina.Interface.HTTPControllers;

import com.example.gestionecucina.Domain.dto.CodaPostazioneDTO;
import com.example.gestionecucina.Domain.dto.NotificaPrepOrdineDTO;
import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.example.gestionecucina.Domain.ports.FrontSignalPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RestAPICucina implements APICucina{

    private FrontSignalPort frontSignalPort;

    @Autowired
    public RestAPICucina(FrontSignalPort frontSignalPort) {
        this.frontSignalPort = frontSignalPort;
    }

    @Override
    public ResponseEntity<CodaPostazioneDTO> getQueue(String ingredienteprincipale) {
        Optional<CodaPostazioneDTO> codaPostazioneDTO = frontSignalPort.getCodaPostazione(ingredienteprincipale);
        if(!codaPostazioneDTO.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(codaPostazioneDTO.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrdineDTO> selectOrder(String ingredienteprincipale) throws JsonProcessingException {
        Optional<OrdineDTO> ordineDTO = frontSignalPort.getOrder(ingredienteprincipale);
        if(!ordineDTO.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ordineDTO.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrdineDTO> completeOrder
            (String ingredienteprincipale,
             NotificaPrepOrdineDTO notificaPrepOrdineDTO)
            throws JsonProcessingException {
        Optional<OrdineDTO> ordineDTO = frontSignalPort.postNotifica(ingredienteprincipale, notificaPrepOrdineDTO);
        if(!ordineDTO.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ordineDTO.get(), HttpStatus.CREATED);
    }

}
