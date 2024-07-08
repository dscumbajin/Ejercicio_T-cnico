package com.example.cuentas;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"com.example.cuentas"}, lazyInit = true)
@EnableFeignClients
@SpringBootApplication
public class CuentasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuentasApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
