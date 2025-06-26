package com.serenitydojo.playwright.domain;

import net.datafaker.Faker;
import java.util.Map;

public record User(
        String first_name,
        String last_name,
        Map<String, String> address,
        String phone,
        String dob,
        String password,
        String email) {
    public static User randomUser() {
        Faker faker = new Faker();
        return new User(
                faker.name().firstName(),
                faker.name().lastName(),
                Map.of(
                        "street", faker.address().streetAddress(),
                        "city", faker.address().city(),
                        "state", faker.address().state(),
                        "country", faker.address().country(),
                        "postal_code", faker.address().postcode()),
                faker.phoneNumber().phoneNumber(),
                "1996-07-06",
                "Az123!sdfdsf*",
                faker.internet().emailAddress()
        );
    }

    public Object withPassword(String password) {
        return new User(
                first_name,
                last_name,
                address,
                phone,
                dob,
                password,
                email);
    }

    public Object withFirstName (String first_name) {
        return new User(
                first_name,
                last_name,
                address,
                phone,
                dob,
                password,
                email);
    }
}
