package com.example.GestioneCucina.Domain;

import com.example.GestioneCucina.Domain.Entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;

public interface TestPort {
    Iterable<ClienteEntity> getClienti();
}
