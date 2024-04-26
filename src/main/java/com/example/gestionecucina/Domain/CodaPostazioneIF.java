package com.example.gestionecucina.Domain;

import java.util.Optional;

public interface CodaPostazioneIF<T> {

    /**
     * inserisci l'ordine specificato nella coda se non viola il vincolo di capacita'
     *
     * @param t ordine da inserire
     * @return {@code true} se è stato aggiunto correttamente, {@code false} altrimenti
     */
    boolean insert(T t);

    /**
     * rimuovi l'elemento in testa alla coda se presente
     *
     * @return Optional contentente la testa della coda se non è vuota, Optional contenente null altrimenti
     */
    Optional<T> remove();

    /**
     * restituisce ma non rimuove l'elemento in testa alla coda se presente
     *
     * @return Optional contentente la testa della coda se non è vuota, Optional contenente null altrimenti
     */
    Optional<T> element();

    /**
     * permette di capire se la coda è piena oppure no
     *
     * @return {@code true} se la coda è piena, {@code false} altrimenti
     */
    boolean isFull();

}
