package com.example.GestioneCucina.Domain;

import com.example.GestioneCucina.Domain.Entity.ClienteEntity;

public interface FrontSignalPort {

    Iterable<ClienteEntity> getClienti();
}
