package com.example.gestionecucina.Domain.Repository;

import com.example.gestionecucina.Domain.Entity.ClienteEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<ClienteEntity, String> {
}
