package com.company.controller;

import com.company.dto.ProjectDTO;
import com.company.dto.ResponseWrapper;
import com.company.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //getProjects
    @GetMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjects() {
        return ResponseEntity
                .ok(new ResponseWrapper("projects successfully retrieved", projectService.listAllProjects(), HttpStatus.OK));
    }

    //getProject
    @GetMapping("/{code}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String code) {
        return ResponseEntity
                .ok(new ResponseWrapper("project successfully retrieved", projectService.getByProjectCode(code), HttpStatus.OK));
    }

    //createProject
    @PostMapping
    @RolesAllowed({"Manager", "Admin"})
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectCode) {
        projectService.save(projectCode);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("project successfully created", HttpStatus.CREATED));
    }

    //updateProject
    @PutMapping
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectCode) {
        projectService.update(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("project successfully updated", HttpStatus.OK));
    }

    //deleteProject
    @DeleteMapping("/{code}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("code") String code) {
        projectService.delete(code);
        return ResponseEntity.ok(new ResponseWrapper("project successfully deleted", HttpStatus.OK));
    }

    //getProjectByManager
    @GetMapping("/manager/project-status")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> getProjectByManager() {
        List<ProjectDTO> projects = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("projects successfully retrieved", projects, HttpStatus.OK));
    }

    //managerCompleteProject
    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed({"Manager"})
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("projects successfully completed", HttpStatus.OK));
    }
}
