package com.example.gestionecucina.Domain;

import org.springframework.stereotype.Service;

@Service
public class AllocatoreOrdini implements BackSignalPort{
    private final CodeIF gestioneCode;

    public AllocatoreOrdini(CodeIF gestioneCode) {
        this.gestioneCode = gestioneCode;
    }
}
