package com.sap.subsystem.vcs_repository.api;

import com.sap.subsystem.vcs_repository.domain.dto.EditVcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.service.VcsRepositoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/repository")
public class VcsRepositoryController {
    private final VcsRepositoryService vcsRepositoryService;

    public VcsRepositoryController(VcsRepositoryService vcsRepositoryService) {
        this.vcsRepositoryService = vcsRepositoryService;
    }

    @GetMapping
    List<VcsRepositoryView> listAllRepositories(){
        return vcsRepositoryService.listAllVcsRepositories();
    }
    @GetMapping("/{id}")
    VcsRepositoryView getVcsRepositoryById(@PathVariable final UUID id){
        return vcsRepositoryService.getByBusinessId(id);
    }
    @PostMapping
    void createVcsRepository(@RequestBody @Valid final VcsRepositoryDto vcsRepositoryDto){
        vcsRepositoryService.saveVcsRepository(vcsRepositoryDto);
    }
    @PatchMapping("/{id}")
    void updateVcsRepository(@PathVariable final UUID id, @RequestBody @Valid final EditVcsRepositoryDto vcsRepositoryDto){
        vcsRepositoryService.editVcsRepository(id, vcsRepositoryDto);
    }
    @DeleteMapping("/{id}")
    void deleteVcsRepository(@PathVariable final UUID id){
        vcsRepositoryService.deleteVcsRepository(id);
    }
}
