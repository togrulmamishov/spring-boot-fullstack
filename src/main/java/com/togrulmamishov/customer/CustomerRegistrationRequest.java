package com.togrulmamishov.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
) {
}
