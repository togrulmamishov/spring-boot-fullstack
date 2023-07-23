package com.togrulmamishov.customer;

import com.togrulmamishov.AbstractTestcontainers;
import com.togrulmamishov.util.Pageable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(
                jdbcTemplate(),
                customerRowMapper);
    }

    @Test
    void selectAllCustomers() {
        String fullName = FAKER.name().fullName();
        // Given
        Customer customer = new Customer(
                fullName, FAKER.internet().safeEmailAddress(), 20);
        underTest.insertCustomer(customer);

        // When
        List<Customer> customers = underTest.selectAllCustomers(new Pageable());

        //Then
        assertThat(customers).isNotEmpty();
        assertThat(customers.stream()
                .map(Customer::getName))
                .contains(fullName);
    }

    @Test
    void selectCustomerById() {
        String fullName = FAKER.name().fullName();
        String email = FAKER.internet().safeEmailAddress();
        // Given
        Customer customer = new Customer(
                fullName,
                email,
                20
        );
        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers(new Pageable())
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(fullName);
            assertThat(c.getEmail()).isEqualTo(email);
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        var id = -1;
        assertThat(underTest.selectCustomerById(id)).isEmpty();
    }

    @Test
    void insertCustomer() {

    }

    @Test
    void existsPersonWithEmail() {
        String fullName = FAKER.name().fullName();
        String email = FAKER.internet().safeEmailAddress();
        // Given
        Customer customer = new Customer(
                fullName,
                email,
                20
        );
        underTest.insertCustomer(customer);
        var actual = underTest.existsPersonWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithId() {
        String fullName = FAKER.name().fullName();
        String email = FAKER.internet().safeEmailAddress();
        // Given
        Customer customer = new Customer(
                fullName, email, 20);
        underTest.insertCustomer(customer);
        var actual = underTest.existsPersonWithId(1);
        assertThat(actual).isTrue();
    }

    @Test
    void willReturnFalseWhenPersonWithItDontExist() {
        String fullName = FAKER.name().fullName();
        String email = FAKER.internet().safeEmailAddress();
        // Given
        Customer customer = new Customer(
                fullName,
                email,
                20
        );
        underTest.insertCustomer(customer);
        var actual = underTest.existsPersonWithId(-1);
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {
        String fullName = FAKER.name().fullName();
        String email = FAKER.internet().safeEmailAddress();
        // Given
        Customer customer = new Customer(
                fullName,
                email,
                20
        );
        underTest.insertCustomer(customer);
        List<Customer> customers = underTest.selectAllCustomers(new Pageable());
        assertThat(customers).isNotEmpty();
        var id = customers.get(0).getId();
        underTest.deleteCustomerById(id);
        assertThat(underTest.selectCustomerById(id)).isEmpty();
    }

    @Test
    void updateCustomer() {
    }
}