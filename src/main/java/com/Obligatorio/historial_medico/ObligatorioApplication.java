package com.Obligatorio.historial_medico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.Obligatorio.historial_medico.repositorio")
public class ObligatorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObligatorioApplication.class, args);
    }
}
