package com.togrulmamishov.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
