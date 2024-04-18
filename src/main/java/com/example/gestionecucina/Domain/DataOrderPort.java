package com.example.GestioneCucina.Domain;

import com.example.GestioneCucina.Domain.Entity.ClienteEntity;

public interface DataOrderPort {
    void getOrderData();
    void setOrderStatus();

    Iterable<ClienteEntity> getClienti();
}
