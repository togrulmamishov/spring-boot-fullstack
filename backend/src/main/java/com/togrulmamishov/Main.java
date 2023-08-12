package com.togrulmamishov;

import com.github.javafaker.Faker;
import com.togrulmamishov.customer.Customer;
import com.togrulmamishov.customer.CustomerRepository;
import com.togrulmamishov.customer.Gender;
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
                var gender = Gender.values()[random.nextInt(2)];
                var customer = new Customer(
                        firstName + " " + lastName,
                        (firstName + "." + lastName).toLowerCase() + "@gmail.com",
                        random.nextInt(16, 99),
                        gender
                );
                customerRepository.save(customer);
            }
        };
    }

}
