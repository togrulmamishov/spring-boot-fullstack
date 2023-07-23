package com.togrulmamishov.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerJPADataAccessServiceTest {

    @InjectMocks
    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        List<Customer> customers = List.of(
                new Customer()
        );
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> customerList = underTest.selectAllCustomers();
        assertThat(customerList).isEqualTo(customers);
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        int id = 1;
        underTest.selectCustomerById(id);
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
    }

    @Test
    void existsPersonWithEmail() {
    }

    @Test
    void existsPersonWithId() {
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomer() {
    }
}