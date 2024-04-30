package com.example.gestionecucina.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe di configurazione per abilitare Hibernate ad usare le interfacce Repository specificate
 */
@EnableJpaRepositories(basePackages = "com.example.gestionecucina.Domain.Repository")
public class JPAConfiguration {
}
