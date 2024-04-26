package com.example.gestionecucina.Domain.Entity;

import com.example.gestionecucina.Domain.CodaPostazioneIF;
import lombok.*;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Coda di una singola postazione della cucina
 */
@Getter
public class CodaPostazioneEntity implements CodaPostazioneIF<OrdineEntity> {

    /**
     * identificativo della coda di postazione
     */
    private String ingredientePrincipale;

    /**
     * numero ordini presenti in coda
     */
    @Setter
    private int numeroOrdiniPresenti;

    /**
     * grado di riempimento attuale della coda
     */
    private double gradoRiempimento;

    /**
     * capacita' massima della coda
     */
    private final int capacita = 5;

    /**
     * coda di ordinazioni
     */
    @Setter
    private Queue<OrdineEntity> queue;

    public CodaPostazioneEntity(String ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.numeroOrdiniPresenti = 0;
        this.gradoRiempimento = 0.0;
        this.queue = new LinkedList<>();
    }

    @Override
    public boolean insert(OrdineEntity ordineEntity){
        if(numeroOrdiniPresenti >= capacita)
            return false;
        boolean status = queue.offer(ordineEntity);
        if(status) {
            this.numeroOrdiniPresenti += 1;
            this.gradoRiempimento = (numeroOrdiniPresenti/capacita);
        }
        return status;
    }

    @Override
    public Optional<OrdineEntity> remove(){
        Optional<OrdineEntity> ordineEntity = Optional.ofNullable(queue.poll());
        if (ordineEntity.isPresent()){
            this.numeroOrdiniPresenti -= 1;
            this.gradoRiempimento = (numeroOrdiniPresenti/capacita);
        }
        return ordineEntity;
    }

    @Override
    public Optional<OrdineEntity> element(){
        Optional<OrdineEntity> ordineEntity = Optional.ofNullable(queue.peek());
        return ordineEntity;
    }

    @Override
    public boolean isFull(){
        return numeroOrdiniPresenti == capacita;
    }

}
