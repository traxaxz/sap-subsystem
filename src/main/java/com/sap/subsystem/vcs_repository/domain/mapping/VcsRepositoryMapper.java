package com.sap.subsystem.vcs_repository.domain.mapping;

import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VcsRepositoryMapper {

    VcsRepository toEntity(VcsRepositoryDto vcsRepositoryDto);
    VcsRepository toEntity(VcsRepositoryView vcsRepositoryView);
    VcsRepositoryView toView(VcsRepository vcsRepository);
    List<VcsRepositoryView> toViews(List<VcsRepository> repositories);

    VcsRepository update(@MappingTarget VcsRepository entityForUpdate, VcsRepository newEntity);
}
