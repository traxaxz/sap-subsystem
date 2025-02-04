package com.sap.subsystem.secret.domain.mapping;

import com.sap.subsystem.secret.domain.dto.SecretDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.domain.model.Secret;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SecretMapper {

    Secret toEntity(final SecretDto secretDto);

    SecretView toView(final Secret secret);

    List<SecretView> toViews(final List<Secret> secrets);

    void update(@MappingTarget final Secret secretForUpdate, final Secret secret);
}
