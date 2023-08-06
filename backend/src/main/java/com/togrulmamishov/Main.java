package com.togrulmamishov;

import com.github.javafaker.Faker;
import com.togrulmamishov.customer.Customer;
import com.togrulmamishov.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            var faker = new Faker();
            var random = new Random();
            for (int i = 0; i < 10; i++) {
                var name = faker.name();
                var firstName = name.firstName();
                var lastName = name.lastName();
                var customer = new Customer(
                        firstName + " " + lastName,
                        (firstName + "." + lastName).toLowerCase() + "@gmail.com",
                        random.nextInt(16, 99)
                );
                customerRepository.save(customer);
            }
        };
    }

}
