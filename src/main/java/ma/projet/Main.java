package ma.projet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ma.projet.test.TestApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Application de Gestion de Stock");

        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner run(TestApplication testApplication) {
        return args -> {
            testApplication.runTests();

            System.out.println("Application terminée avec succès!");
        };
    }
}