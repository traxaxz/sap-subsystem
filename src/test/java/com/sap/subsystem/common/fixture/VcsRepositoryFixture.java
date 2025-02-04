package com.sap.subsystem.common.fixture;

import java.util.Set;
import java.util.UUID;

import com.github.javafaker.Faker;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;

public final class VcsRepositoryFixture {
    private static final Faker FAKER  = new Faker();

    private VcsRepositoryFixture(){}

    public static VcsRepository create(){
        return new VcsRepository()
                .setRepository(FAKER.name().title())
                .setBusinessId(UUID.fromString(FAKER.internet().uuid()))
                .setSecrets(Set.of(SecretFixture.create()));
    }

    public static VcsRepository create(final Secret secret){
        return new VcsRepository()
                .setRepository(FAKER.name().title())
                .setBusinessId(UUID.fromString(FAKER.internet().uuid()))
                .setSecrets(Set.of(secret));
    }
}
