package com.angMetal.orders.service;

import com.angMetal.orders.entity.Project;
import com.angMetal.orders.repositories.ProjectRepository;
import com.angMetal.orders.entity.Client;
import com.angMetal.orders.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    // Constructor-based injection
    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Helper method to validate that both client and company are not null
    private void validateProject(Project project) {
        if (project.getClient() == null) {
            throw new IllegalArgumentException("Client is required for the project.");
        }
        if (project.getCompany() == null) {
            throw new IllegalArgumentException("Company is required for the project.");
        }
    }

    // Create a new project
    @Transactional
    public Project createProject(Project project) {
        // Validate the project to ensure client and company are set
        validateProject(project);

        // Save the project and return it
        return projectRepository.save(project);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get a project by its ID
    public Optional<Project> getProjectById(Long projetID) {
        return projectRepository.findById(projetID);
    }

    // Update an existing project
    @Transactional
    public Project updateProject(Long projetID, Project projectDetails) {
        // Check if the project exists
        Optional<Project> optionalProject = projectRepository.findById(projetID);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            // Validate the project to ensure client and company are set
            validateProject(projectDetails);

            // Update fields of the project
            project.setNomProjet(projectDetails.getNomProjet());
            project.setDateDebut(projectDetails.getDateDebut());
            project.setDateFin(projectDetails.getDateFin());
            project.setStatut(projectDetails.getStatut());
            project.setClient(projectDetails.getClient());
            project.setCompany(projectDetails.getCompany());

            // Save and return the updated project
            return projectRepository.save(project);
        }
        return null;  // or throw an exception if not found
    }

    // Delete a project by its ID
    @Transactional
    public boolean deleteProject(Long projetID) {
        // Check if the project exists
        if (projectRepository.existsById(projetID)) {
            projectRepository.deleteById(projetID);
            return true;
        }
        return false;  // or throw an exception if not found
    }
}
