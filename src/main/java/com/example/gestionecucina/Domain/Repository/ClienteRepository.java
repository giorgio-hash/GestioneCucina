package com.example.GestioneCucina.Domain.Repository;

import com.example.GestioneCucina.Domain.Entity.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<ClienteEntity, String> {
}
