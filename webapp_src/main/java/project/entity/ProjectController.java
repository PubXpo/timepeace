package com.tsheetweb.project.entity;

import com.tsheetweb.contractor.entity.Contractor;
import com.tsheetweb.contractor.entity.ContractorRepository;
import com.tsheetweb.contractor.profile.ContractorProfile;
import com.tsheetweb.contractor.profile.ContractorProfileRepository;
import com.tsheetweb.project.category.Category;
import com.tsheetweb.task.entity.Task;
import com.tsheetweb.task.entity.TaskRepository;
import com.tsheetweb.task.entity.TaskService;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsRepository;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ContractorRepository contractorRepo;

    @Autowired
    private ContractorProfileRepository profileRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private TaskTotalsRepository taskTotalsRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskTotalsService taskTotalsService;

    // Create
    @GetMapping("/{id}/project/new")
    public String showNewProjectForm(@PathVariable("id") long id, Model model, Project project){
        getContractorData(id,model);
        model.addAttribute("project", new Project());
        return "/project/project";
    }

    @PostMapping("/project/save")
    public String saveProject( Project project, @Valid Contractor contractor){
        projectService.ranIdGen(project);
        if (!projectService.checkIfProjectExist(project.getContractor().getId(), project)){
            projectRepo.save(project);
        } else {
            throw new RuntimeException();
        }
        return "redirect:/"+ project.getContractor().getId()+"/contractor";
    }

    // Read
    public void getContractorData(long id, Model model){
        Contractor contractor = contractorRepo.findById(id).get();
        List<Contractor> contractorProfile = contractorRepo.findContractorID(id);
        List<Project> listProjects = projectRepo.findProjectByContractorID(id);
        List<ContractorProfile> listProfile = profileRepo.findProfileByContractorID(id);

        model.addAttribute("contractor", contractor);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listProfile", listProfile);
        model.addAttribute("profile", contractorProfile);

    }

    @GetMapping("/contractor={c_id}/project=ALL")
    public String showAddProjects(@PathVariable("c_id") long c_id, Model model, Project project, String projectID, RedirectAttributes ra){
        getContractorData(c_id,model);
        List<TaskTotals> totalsByContractorID = taskTotalsService.findTaskTotalsByContractorID(c_id);

        //get chart data for average weeks
        Map<String, Double> averageHourPlots = taskTotalsService.plotDailyHours(c_id);

        model.addAttribute("totalsByContractorID", totalsByContractorID);
        model.addAttribute("averageHourPlots", averageHourPlots);
        model.addAttribute("success", "it's got it "+project.getP_id());

        return "/project/all_projects";
    }


    // Update
    @GetMapping("{c_id}/project/{id}/edit")
    public String showProjectUpdateForm(@PathVariable("c_id") long c_id, @PathVariable("id") long id, Model model) {
        getContractorData(c_id, model);
        projectService.getProjectData(id,model);
        taskService.getTaskDataByPID(id,model);
        model.addAttribute("task", new Task());
        model.addAttribute("newCategory", new Category());
        return "/project/project";
    }

    @PostMapping("/{c_id}/project/{id}/update")
    public String updateProject(@PathVariable Long id, @PathVariable("c_id") long c_id, @Valid Project project) {
      projectService.updateProjectItem(c_id, id, project);
        return "redirect:/" + c_id + "/project/" + id + "/edit";
    }

    // Delete
    @GetMapping("{c_id}/project/{id}/delete")
    public String deleteProject(@PathVariable("id") long id,@PathVariable("c_id") long c_id, Model model,  @Valid Contractor contractor) {
        projectRepo.deleteById(id);
        return "redirect:/"+c_id+"/contractor";
    }
}
