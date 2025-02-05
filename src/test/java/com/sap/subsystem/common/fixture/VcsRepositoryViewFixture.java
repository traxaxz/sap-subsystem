package com.sap.subsystem.common.fixture;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import com.github.javafaker.Faker;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;

public final class VcsRepositoryViewFixture {
    private static final Faker FAKER  = new Faker();

    private VcsRepositoryViewFixture(){}

    public static VcsRepositoryView create(){
        return new VcsRepositoryView(
                FAKER.internet().uuid(),
                FAKER.name().title(),
                SecretViewFixture.createMultiple());
    }

    public static List<VcsRepositoryView> createMultiple(){
        return Stream.generate(VcsRepositoryViewFixture::create).limit(5).toList();
    }
}
