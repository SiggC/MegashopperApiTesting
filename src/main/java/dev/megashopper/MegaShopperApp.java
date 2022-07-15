package dev.megashopper;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class MegaShopperApp {

    public static void main(String[] args) {
        SpringApplication.run(MegaShopperApp.class, args);
    }
}

