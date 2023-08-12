package com.togrulmamishov.journey;

import com.togrulmamishov.customer.Customer;
import com.togrulmamishov.customer.CustomerRegistrationRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

import static com.togrulmamishov.customer.Gender.MALE;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    public static final Random RANDOM = new Random();

    @Test
    void canRegisterACustomer() {
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.name();
        String email = fakerName.lastName() + UUID.randomUUID() + "@gmail.com";
        Integer age = RANDOM.nextInt(1, 100);
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(name, email, age, MALE.name());
        testClient.post()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        var allCustomers = testClient.get()
                .uri("api/v1/customers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        Customer expectedCustomer = new Customer(name, email, age, MALE);

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);
    }
}
