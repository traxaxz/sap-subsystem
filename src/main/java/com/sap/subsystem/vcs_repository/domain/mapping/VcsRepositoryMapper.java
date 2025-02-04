package com.sap.subsystem.vcs_repository.domain.mapping;

import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiResponseDto;
import com.sap.subsystem.secret.domain.dto.SecretView;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VcsRepositoryMapper {

    VcsRepository toEntity(VcsRepositoryDto vcsRepositoryDto);

    @Mapping(target = "secrets", ignore = true)
    VcsRepository toEntity(EditVcsRepositoryDto vcsRepositoryDto);

    @Mapping(target = "secrets", source = "secrets", qualifiedByName = "mapSecrets")
    VcsRepositoryView toView(VcsRepository vcsRepository);

    List<VcsRepositoryView> toViews(List<VcsRepository> repositories);

    VcsRepository update(@MappingTarget VcsRepository entityForUpdate, VcsRepository newEntity);

    @Mapping(target = "repository", source = "name")
    VcsRepository toEntity(GithubRepositoryApiResponseDto createdRepository);

    @Named("mapSecrets")
    default Set<SecretView> mapSecrets(final Set<Secret> secrets){
        return secrets.stream()
                .map(secret -> new SecretView(
                        secret.getBusinessId(),
                        secret.getSecret()
                ))
                .collect(Collectors.toSet());
    }
}
