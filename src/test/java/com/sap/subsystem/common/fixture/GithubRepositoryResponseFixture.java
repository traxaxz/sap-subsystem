package com.sap.subsystem.common.fixture;

import java.util.List;

import com.github.javafaker.Faker;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiResponseDto;

public final class GithubRepositoryResponseFixture {
    private static final Faker FAKER = new Faker();

    private GithubRepositoryResponseFixture(){}

    public static GithubRepositoryApiResponseDto create(){
        return new GithubRepositoryApiResponseDto(
                FAKER.name().title(),
                FAKER.internet().uuid()
        );
    }
}
