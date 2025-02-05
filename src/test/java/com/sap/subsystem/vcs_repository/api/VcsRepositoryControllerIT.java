package com.sap.subsystem.vcs_repository.api;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.subsystem.common.fixture.GithubRepositoryApiResponseDtoFixture;
import com.sap.subsystem.common.fixture.GithubRepositoryResponseFixture;
import com.sap.subsystem.common.fixture.SecretFixture;
import com.sap.subsystem.common.fixture.VcsRepositoryDtoFixture;
import com.sap.subsystem.common.fixture.VcsRepositoryFixture;
import com.sap.subsystem.github_api.domain.dto.GithubRepositoryApiResponseDto;
import com.sap.subsystem.github_api.service.GithubRepositoryApiService;
import com.sap.subsystem.secret.domain.model.Secret;
import com.sap.subsystem.secret.repository.SecretRepository;
import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.domain.model.VcsRepository;
import com.sap.subsystem.vcs_repository.facade.VcsRepositoryFacade;
import com.sap.subsystem.vcs_repository.repository.VcsRepositoryRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class VcsRepositoryControllerIT {

    @Autowired
    private VcsRepositoryFacade vcsRepositoryFacade;
    @Autowired
    private VcsRepositoryRepo vcsRepositoryRepo;
    @Autowired
    private SecretRepository secretRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GithubRepositoryApiService githubRepositoryApiService;


    @BeforeEach
    void cleanUp(){
        vcsRepositoryRepo.deleteAllInBatch();
    }

    @Test
    void listAllRepositories_shouldReturnList() throws Exception {
        final VcsRepository savedRepo = vcsRepositoryRepo.save(VcsRepositoryFixture.create());

        final MvcResult mvcResult = mockMvc.perform(get("/repository"))
                .andExpect(status().isOk())
                .andReturn();

        final List<VcsRepositoryView> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actual).isNotEmpty();
    }

    @Test
    void getByBusinessId_shouldReturn_VcsRepositoryView() throws Exception {
        final VcsRepository savedRepo = vcsRepositoryRepo.save(VcsRepositoryFixture.create());

        final MvcResult mvcResult = mockMvc.perform(get("/repository/{id}",savedRepo.getBusinessId()))
                .andExpect(status().isOk())
                .andReturn();

        final VcsRepositoryView actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actual).isNotNull();
        assertThat(actual.repository()).isEqualTo(savedRepo.getRepository());
    }

    @Test
    void createVcsRepository_shouldCreateRecord() throws Exception {
        final VcsRepositoryDto request = VcsRepositoryDtoFixture.create();
        final GithubRepositoryApiResponseDto responseDto = GithubRepositoryResponseFixture.create();
        given(githubRepositoryApiService.createRepository(any(VcsRepositoryDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(post("/repository")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        final Optional<VcsRepository> afterSave = vcsRepositoryRepo.getByBusinessId(responseDto.businessId());

        assertThat(afterSave).isPresent();
        assertThat(afterSave.get().getRepository()).isEqualTo(responseDto.name());
    }

    @Test
    void updateVcsRepository_shouldUpdateRecord() throws Exception {
        final VcsRepository savedRepo = vcsRepositoryRepo.save(VcsRepositoryFixture.create());
        final GithubRepositoryApiResponseDto responseDto = GithubRepositoryApiResponseDtoFixture.create();

        given(githubRepositoryApiService.updateRepository(anyString(), any(EditVcsRepositoryDto.class)))
                .willReturn(responseDto);

        mockMvc.perform(patch("/repository/{id}", savedRepo.getBusinessId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedRepo.setRepository(responseDto.name()))))
                .andExpect(status().isOk());

        final VcsRepository updatedRepo = vcsRepositoryRepo.getByBusinessId(savedRepo.getBusinessId()).orElseThrow();
        assertThat(updatedRepo.getRepository()).isEqualTo(responseDto.name());
    }

    @Test
    void deleteVcsRepository_shouldDeleteRecord() throws Exception {
        final VcsRepository savedRepo = vcsRepositoryRepo.save(VcsRepositoryFixture.create());

        mockMvc.perform(delete("/repository/{id}", savedRepo.getBusinessId()))
                .andExpect(status().isOk());

        final Optional<VcsRepository> deletedRepo = vcsRepositoryRepo.getByBusinessId(savedRepo.getBusinessId());

        assertThat(deletedRepo).isEmpty();
    }

    @Test
    void isRepositorySecretValid_shouldReturnOK() throws Exception {
        final Secret secret = SecretFixture.create();
        secretRepository.save(secret);
        final VcsRepository vcsRepository = VcsRepositoryFixture.create();
        final VcsRepository savedRepo = vcsRepositoryRepo.save(vcsRepository);
        final VcsRepositoryDto request = new VcsRepositoryDto(savedRepo.getRepository(), savedRepo.getSecrets().stream().findFirst().get().getBusinessId());

        mockMvc.perform(post("/repository/secret/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
