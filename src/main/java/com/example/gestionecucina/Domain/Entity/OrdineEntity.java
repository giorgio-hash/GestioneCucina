package com.example.gestionecucina.Domain.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.security.Timestamp;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ordine", schema = "serveeasy", catalog = "")
public class OrdineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable = false, insertable = false, updatable = false)
    private int id;

    @Column(name = "ID_COMANDA", nullable = false, updatable = false)
    private int idComanda;

    @Basic
    @Column(name = "ID_piatto", nullable = false, length = 20)
    private String idPiatto;

    @Basic
    @Column(name = "stato", insertable = false, updatable = true)
    private Integer stato;

    @Basic
    @Column(name = "urgenza_cliente", insertable = false, updatable = true)
    private Timestamp tOrdinazione;

    @Column(name = "urgenza_cliente", insertable = false, updatable = true)
    private Integer urgenzaCliente;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdineEntity that)) return false;
        return getId() == that.getId()
                && getIdComanda() == that.getIdComanda()
                && Objects.equals(getIdPiatto(), that.getIdPiatto())
                && Objects.equals(getStato(), that.getStato())
                && Objects.equals(tOrdinazione, that.tOrdinazione)
                && Objects.equals(getUrgenzaCliente(), that.getUrgenzaCliente());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getIdComanda(),
                getIdComanda(),
                getStato(),
                tOrdinazione,
                getUrgenzaCliente());
    }

}