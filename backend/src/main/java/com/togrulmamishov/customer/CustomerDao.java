package com.togrulmamishov.customer;

import com.togrulmamishov.util.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers(Pageable pageable);

    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);

    boolean existsPersonWithEmail(String email);

    boolean existsPersonWithId(Integer id);

    void deleteCustomerById(Integer customerId);

    void updateCustomer(Customer update);
}
