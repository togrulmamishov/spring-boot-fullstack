package com.togrulmamishov.customer;

import com.togrulmamishov.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.togrulmamishov.customer.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerService underTest;

    @Captor
    private ArgumentCaptor<Customer> argumentCaptor;

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();

        verify(customerDao).selectAllCustomers();
    }

    @Test
    @DisplayName("Can get customer by ID")
    void canGetCustomer() {
        int id = 10;
        Customer customer = new Customer(
                id,
                "Alex",
                "alex@gmail.com",
                20,
                MALE);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        Customer actual = underTest.getCustomer(id);
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void shouldAddCustomer() {
        Customer customer = new Customer(
                "Alex",
                "alex@gmail.com",
                20,
                MALE);
        underTest.addCustomer(
                new CustomerRegistrationRequest(customer.getName(), customer.getEmail(), customer.getAge(), MALE.name()));
        verify(customerDao).insertCustomer(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(customer);
    }

    @Test
    void deleteCustomerById() {
        int id = 10;
        when(customerDao.existsPersonWithId(id)).thenReturn(true);
        underTest.deleteCustomerById(id);
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void deleteCustomerById_willThrowException_whenEmailNotExists() {
        int id = 10;
        when(customerDao.existsPersonWithId(id)).thenReturn(false);
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
        verify(customerDao, never()).deleteCustomerById(id);
    }
}