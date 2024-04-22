package com.example.gestionecucina.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.gestionecucina.Domain.Repository")
public class JPAConfiguration {
}
