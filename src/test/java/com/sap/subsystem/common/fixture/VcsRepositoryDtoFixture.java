package com.sap.subsystem.common.fixture;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import com.github.javafaker.Faker;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;

public final class VcsRepositoryDtoFixture {
    private static final Faker FAKER  = new Faker();

    private VcsRepositoryDtoFixture(){}

    public static VcsRepositoryDto create(){
        return new VcsRepositoryDto(
                FAKER.name().title(),
                UUID.fromString(FAKER.internet().uuid())
        );
    }

    public static List<VcsRepositoryDto> createMultiple(){
        return Stream.generate(VcsRepositoryDtoFixture::create).limit(5).toList();
    }
}
