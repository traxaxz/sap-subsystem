package com.sap.subsystem.vcs_repository.api;

import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.facade.VcsRepositoryFacade;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
/**
 * Endpoints to the vcsRepository(Version Control System Repository)
 *
 * @author danail.zlatanov
 * */
@RestController
@RequestMapping("/repository")
public class VcsRepositoryController {
    private final VcsRepositoryFacade vcsRepositoryFacade;

    public VcsRepositoryController(final VcsRepositoryFacade vcsRepositoryFacade) {
        this.vcsRepositoryFacade = vcsRepositoryFacade;
    }

    @GetMapping
    List<VcsRepositoryView> listAllRepositories() {
        return vcsRepositoryFacade.listAllVcsRepositories();
    }

    @GetMapping("/{id}")
    VcsRepositoryView getVcsRepositoryById(@PathVariable final UUID id){
        return vcsRepositoryFacade.getByBusinessId(id);
    }

    @PostMapping
    void createVcsRepository(@RequestBody @Valid final VcsRepositoryDto vcsRepositoryDto){
        vcsRepositoryFacade.createVcsRepository(vcsRepositoryDto);
    }

    @PatchMapping("/{id}")
    void updateVcsRepository(@PathVariable final UUID id, @RequestBody @Valid final EditVcsRepositoryDto vcsRepositoryDto){
        vcsRepositoryFacade.editVcsRepository(id, vcsRepositoryDto);
    }

    @DeleteMapping("/{id}")
    void deleteVcsRepository(@PathVariable final UUID id){
        vcsRepositoryFacade.deleteVcsRepository(id);
    }

    @PostMapping("/secret/validate")
    void isRepositorySecretValid(@RequestBody final VcsRepositoryDto vcsRepositoryDto){
        vcsRepositoryFacade.validateSecret(vcsRepositoryDto);
    }
}
