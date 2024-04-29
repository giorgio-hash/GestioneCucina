package com.example.gestionecucina.Domain;

import java.util.List;

public interface DataPort {

    /**
     * Estrae gli ID degli ingredienti principali presenti nel database
     * @return Iterable di ID ingredienti principali
     */
    List<String> getIdIngredienti();
}
