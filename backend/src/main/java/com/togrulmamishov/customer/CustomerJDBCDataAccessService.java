package com.togrulmamishov.customer;

import com.togrulmamishov.util.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate,
                                         CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers(Pageable pageable) {
        var sql = """
                SELECT c.id,
                       c.name,
                       c.age,
                       c.email,
                       c.gender
                FROM customer c
                ORDER BY c.id ASC
                LIMIT ?
                OFFSET ?
                """;
        return jdbcTemplate.query(
                sql, customerRowMapper, pageable.getSize(), pageable.getOffset());
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT c.id,
                       c.name,
                       c.age,
                       c.email,
                       c.gender
                FROM customer c
                ORDER BY c.id
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT c.id,
                       c.name,
                       c.age,
                       c.email,
                       c.gender
                FROM customer c
                WHERE c.id = ?
                """;
        return jdbcTemplate
                .query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age, gender)
                VALUES (?, ?, ?, ?)
                """;
        jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge(),
                customer.getGender().name());
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT EXISTS(SELECT 1
                              FROM customer
                              WHERE email = ?)
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, email);

    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        var sql = """
                SELECT EXISTS(SELECT 1
                              FROM customer
                              WHERE id = ?)
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        var sql = """
                DELETE
                FROM customer
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, customerId);
    }

    @Override
    public void updateCustomer(Customer customer) {
        var sql = """
                UPDATE customer
                SET name = ?,
                    age = ?,
                    email = ?,
                    gender = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getAge(),
                customer.getEmail(),
                customer.getId(),
                customer.getGender().name());
    }
}
