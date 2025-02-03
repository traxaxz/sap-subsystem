package com.sap.subsystem.github_api.domain.mapping;

import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiRequestDto;
import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GithubApiMapper {

    @Mapping(target = "name", source = "repository")
    GithubRepositoryApiRequestDto toGithubDto(final VcsRepositoryDto vcsRepositoryDto);

    @Mapping(target = "name", source = "repository")
    GithubRepositoryApiRequestDto toGithubDto(final EditVcsRepositoryDto vcsRepositoryDto);
}
