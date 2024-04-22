package com.example.gestionecucina.Interface.HTTPControllers;

import org.springframework.http.ResponseEntity;

public interface APICucina {

    ResponseEntity getQueue();
    ResponseEntity selectOrder();
    ResponseEntity completeOrder();
}
