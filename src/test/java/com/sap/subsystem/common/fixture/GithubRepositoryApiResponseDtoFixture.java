package com.sap.subsystem.common.fixture;

import com.github.javafaker.Faker;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiResponseDto;

public final class GithubRepositoryApiResponseDtoFixture {
    private static final Faker FAKER = new Faker();

    private GithubRepositoryApiResponseDtoFixture(){}

    public static GithubRepositoryApiResponseDto create(){
        return new GithubRepositoryApiResponseDto(
                FAKER.name().title(),
                FAKER.internet().uuid()
        );
    }
}
