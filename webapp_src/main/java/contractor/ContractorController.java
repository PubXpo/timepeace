package com.tsheetweb.contractor.entity;

import com.tsheetweb.contractor.profile.ContractorProfileService;
import com.tsheetweb.project.entity.Project;
import com.tsheetweb.project.entity.ProjectService;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;

@Controller
public class ContractorController {

    @Autowired
    private ContractorService contractorService;

    @Autowired
    private TaskTotalsService taskTotalsService;

    @Autowired
    private ContractorProfileService profileService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("contractor", new Contractor());
        return "index";
    }

    @GetMapping("/login")
    public String contractorLogin() {
        return "login";
    }


    //Create
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("contractor", new Contractor());
        return "/contractor/registration";
    }

    @PostMapping("process_register")
    @ResponseStatus (HttpStatus.CREATED)
    public String process_register(Contractor contractor) {
        contractorService.ranIdGen(contractor);
        contractorService.securePassword(contractor);
        contractorService.saveInDB(contractor);
        return "registration_success";
    }

    // Read
    @GetMapping("/contractors")
    public String findProfile(Principal principal, Model model) {
        Contractor findProfile = contractorService.getContractorByEmail(principal.getName());
        model.addAttribute("findProfile", findProfile);
        return "redirect:/" + findProfile.getId() + "/contractor";
    }

    @GetMapping("{id}/contractor")
    public String contractorHome(@PathVariable("id") long id, Model model) {
        getContractorData(id, model);
        taskTotalsService.calculateDashboardData(id, model);
        return "/contractor/dashboard";
    }

    public void getContractorData(long id, Model model){
        model.addAttribute("contractor", contractorService.getContactor(id));
        model.addAttribute("profile", contractorService.getContractorListData(id));
        model.addAttribute("listProfile",profileService.getContractorProfileListData(id));
        model.addAttribute("listProjects",projectService.getProjectList(id));
    }


    // update
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        getContractorData(id, model);
        return "/contractor/update_security";
    }

    @PutMapping("/contractor/{id}/update")
    public void contractorUpdate(@RequestBody Contractor contractor) {
        contractorService.securePassword(contractor);
        contractorService.saveInDB(contractor);
    }
}
