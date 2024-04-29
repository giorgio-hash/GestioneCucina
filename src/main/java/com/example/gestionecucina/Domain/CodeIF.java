package com.example.gestionecucina.Domain;

import com.example.gestionecucina.Domain.Entity.OrdineEntity;
import com.example.gestionecucina.Domain.dto.OrdineDTO;

public interface CodeIF {

    void push(OrdineDTO dto);
}
