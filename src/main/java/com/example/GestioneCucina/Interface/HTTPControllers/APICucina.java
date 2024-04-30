<<<<<<< Updated upstream
package com.example.gestionecucina.Interface.HTTPControllers;

import org.springframework.http.ResponseEntity;

public interface APICucina {

    ResponseEntity getQueue();
    ResponseEntity selectOrder();
    ResponseEntity completeOrder();
=======
package com.example.GestioneCucina.Interface.HTTPControllers;

public interface APICucina {
    /**
     * Riceve un messaggio tramite Kafka dal servizio gestioneCucina in merito all'avvenuta ordinazione
     * da parte di un cliente
     *
     * @param message il corpo del messaggio vero e proprio
     * @param topic topic del message broker sul quale si riceve il messaggio
     * @param partition numero di partizione sul quale si riceve il messaggio
     * @param offset numero di offset che presenta il messaggio ricevuto
     */
    void receive(String message, String topic, Integer partition, Long offset);

    /**
     * Restituisce l'ultimo messaggio letto dal listener
     *
     * @return l'ultimo messaggio letto dal listener
     */
    String getLastMessageReceived();
>>>>>>> Stashed changes
}
