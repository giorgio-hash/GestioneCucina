package com.example.gestionecucina.Domain;

import com.example.gestionecucina.Domain.dto.OrdineDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CodeIF {

    void push(OrdineDTO dto) throws JsonProcessingException;
}
