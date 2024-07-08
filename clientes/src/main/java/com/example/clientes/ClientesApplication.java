package com.example.clientes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"com.example.clientes"}, lazyInit = true)
@SpringBootApplication
public class ClientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientesApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
