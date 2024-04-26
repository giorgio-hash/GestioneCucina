package com.example.gestionecucina.Domain.dto;

import com.example.gestionecucina.Domain.CodaPostazioneIF;
import lombok.*;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Coda di una singola postazione della cucina
 */
@Getter
public class CodaPostazioneDTO implements CodaPostazioneIF<OrdineDTO> {

    /**
     * identificativo della coda di postazione (IN MAIUSCOLO)
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
    private Queue<OrdineDTO> queue;

    public CodaPostazioneDTO(String ingredientePrincipale) {
        this.ingredientePrincipale = ingredientePrincipale;
        this.numeroOrdiniPresenti = 0;
        this.gradoRiempimento = 0.0;
        this.queue = new LinkedList<>();
    }

    @Override
    public boolean insert(OrdineDTO ordineDTO) {
        if (numeroOrdiniPresenti >= capacita)
            return false;
        boolean status = queue.offer(ordineDTO);
        if (status) {
            this.numeroOrdiniPresenti += 1;
            this.gradoRiempimento = (numeroOrdiniPresenti / capacita);
        }
        return status;
    }

    @Override
    public Optional<OrdineDTO> remove() {
        Optional<OrdineDTO> ordineDTO = Optional.ofNullable(queue.poll());
        if (ordineDTO.isPresent()) {
            this.numeroOrdiniPresenti -= 1;
            this.gradoRiempimento = (numeroOrdiniPresenti / capacita);
        }
        return ordineDTO;
    }

    @Override
    public Optional<OrdineDTO> element() {
        Optional<OrdineDTO> ordineDTO = Optional.ofNullable(queue.peek());
        return ordineDTO;
    }

    @Override
    public boolean isFull() {
        return numeroOrdiniPresenti == capacita;
    }

}