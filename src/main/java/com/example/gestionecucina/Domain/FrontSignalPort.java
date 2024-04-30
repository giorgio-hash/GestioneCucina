package com.example.gestionecucina.Domain;

public interface FrontSignalPort {
   void getQueue();
   void selectOrder();
   void completeOrder();
}
