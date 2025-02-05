package com.sap.subsystem.common.fixture;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javafaker.Faker;
import com.sap.subsystem.secret.domain.dto.SecretView;

public final class SecretViewFixture {
    private static final Faker FAKER  = new Faker();

    private SecretViewFixture(){}

    public static SecretView create(){
        return new SecretView(
                FAKER.internet().uuid(),
                FAKER.name().title());
    }

    public static Set<SecretView> createMultiple(){
        return Stream.generate(SecretViewFixture::create).limit(5).collect(Collectors.toSet());
    }
}
