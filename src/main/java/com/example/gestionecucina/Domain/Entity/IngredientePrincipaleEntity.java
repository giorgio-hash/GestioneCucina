package com.example.gestionecucina.Domain.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "IngredientePrincipale", schema = "serveeasy" , catalog = "")
public class IngredientePrincipaleEntity {

    @Id
    @Column(name = "ID", nullable = false, length = 20)
    private String id;
    @Basic
    @Column(name = "nome", nullable = false, length = 20)
    private String nome;
}