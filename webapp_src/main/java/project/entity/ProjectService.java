package com.tsheetweb.project.entity;

import com.tsheetweb.contractor.entity.Contractor;
import com.tsheetweb.contractor.entity.ContractorRepository;
import com.tsheetweb.contractor.entity.ContractorSecConfig;
import com.tsheetweb.project.category.Category;
import com.tsheetweb.project.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService implements UserDetailsService {
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ContractorRepository contractorRepo;

    @Autowired
    CategoryService categoryService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Contractor contractor = contractorRepo.findByEmail(email);
        if (contractor == null){
            throw new UsernameNotFoundException("Contractor not Found");
        }
        return new ContractorSecConfig(contractor);
    }

    public void getProjectData(long id, Model model){
        Project project = projectRepo.findById(id).get();
        List<Category> listCategories = categoryService.listCategories(id);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("project", project);
    }

    public Project getProject(long projectId){
        return projectRepo.findById(projectId).get();
    }

    public List<Project> getProjectList(long contractorId){
        return  projectRepo.findProjectByContractorID(contractorId);
    }


    public void ranIdGen(Project project){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        project.setP_id(rightNow.getTimeInMillis());
    }

    public boolean checkIfProjectExist(long c_id, Project p) {
        List<Project> projectList = projectRepo.findProjectByContractorID(c_id);
        boolean projectExist = false;

        String newContractorProjectCombination = c_id + p.getProjectNumber();

        for (Project eachProject : projectList) {
            String existingContractorProjectCombination = c_id + eachProject.getProjectNumber();

            if (newContractorProjectCombination.equals(existingContractorProjectCombination)) {
                projectExist = true;
                break;
            }
        }
        return projectExist;

    }

}
