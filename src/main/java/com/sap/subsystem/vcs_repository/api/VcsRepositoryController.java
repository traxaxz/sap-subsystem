package com.sap.subsystem.vcs_repository.api;

import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryDto;
import com.sap.subsystem.vcs_repository.domain.dto.VcsRepositoryView;
import com.sap.subsystem.vcs_repository.service.VcsRepositoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "repository")
@Validated
public class VcsRepositoryController {
    private final VcsRepositoryService vcsRepositoryService;

    public VcsRepositoryController(VcsRepositoryService vcsRepositoryService) {
        this.vcsRepositoryService = vcsRepositoryService;
    }

    @GetMapping
    List<VcsRepositoryView> listAllRepositories(){
        return vcsRepositoryService.listAllVcsRepositories();
    }
    @GetMapping("/${id}")
    VcsRepositoryView getVcsRepositoryById(@RequestParam final String id){
        return vcsRepositoryService.getByBusinessId(id);
    }
    @PostMapping
    void createVcsRepository(@RequestBody final VcsRepositoryDto vcsRepositoryDto){
        vcsRepositoryService.saveVcsRepository(vcsRepositoryDto);
    }
    @PatchMapping("/${id}")
    void updateVcsRepository(@RequestParam final String id, @RequestBody final VcsRepositoryDto vcsRepositoryDto){
        vcsRepositoryService.editVcsRepository(id, vcsRepositoryDto);
    }
    @DeleteMapping("/${id}")
    void deleteVcsRepository(@RequestParam final String id){
        vcsRepositoryService.deleteVcsRepository(id);
    }
}
