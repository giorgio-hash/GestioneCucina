package com.example.GestioneCucina.Domain;

import com.example.GestioneCucina.Domain.Entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestioneOrdini{
    private final DataOrderPort DataOrderPort;

    @Autowired
    public GestioneOrdini(DataOrderPort DataOrderPort) {
        this.DataOrderPort = DataOrderPort;
    }

}
