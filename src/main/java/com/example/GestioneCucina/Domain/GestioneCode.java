package com.example.GestioneCucina.Domain;

<<<<<<< Updated upstream
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GestioneCode implements FrontSignalPort{


    private final CodeIF[] allocatoreOrdini;
    private Object[] tableau;

    public GestioneCode(CodeIF[] allocatoreOrdini) {
        this.allocatoreOrdini = allocatoreOrdini;
    }

    // Constructor
    public void PileArray(CodeIF allocationOrdini) {
        this.capacite = capacite;
        this.tableau = new Object[capacite];
        this.taille = 0;
    }

    // Add un elemento nella pile
    void push(Object element) {
        if (taille == capacite) {
            capacite *= 2;
            tableau = Arrays.copyOf(tableau, capacite);
        }
        tableau[taille++] = element;
    }

    @Override
    public void getQueue() {

    }

    @Override
    public void selectOrder() {

    }

    @Override
    public void completeOrder() {

    }
=======
import org.springframework.stereotype.Service;

@Service
public class GestioneCode {

>>>>>>> Stashed changes
}
