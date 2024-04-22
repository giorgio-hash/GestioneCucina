package com.example.GestioneCucina.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.example.GestioneCucina.Domain.Repository")
public class JPAConfiguration {
}
