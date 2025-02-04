package com.sap.subsystem.common.fixture;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.javafaker.Faker;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.domain.model.Secret;

public final class SecretFixture {
    private static final Faker FAKER  = new Faker();

    private SecretFixture(){}

    public static Secret create() {
        return new Secret()
                .setSecret(FAKER.name().title())
                .setBusinessId(UUID.fromString(FAKER.internet().uuid()));
    }
}
