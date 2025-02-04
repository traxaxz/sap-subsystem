package com.sap.subsystem.common.fixture;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;
import com.sap.subsystem.github_api.domain.dto.GithubSecretDto;

public final class GithubSecretDtoFixture {
    private static final Faker FAKER = new Faker();

    private GithubSecretDtoFixture(){}


    public static GithubSecretDto create(){
        return new GithubSecretDto(
                FAKER.name().title(),
                FAKER.date().past(365, TimeUnit.DAYS)
                .toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime().toString(),
                FAKER.date().past(30, TimeUnit.DAYS)
                .toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime().toString()
        );
    }

}
