package com.tsheetweb.contractor.entity;

import com.tsheetweb.contractor.profile.ContractorProfile;
import com.tsheetweb.contractor.profile.ContractorProfileRepository;
import com.tsheetweb.project.entity.Project;
import com.tsheetweb.project.entity.ProjectRepository;
import com.tsheetweb.project.entity.ProjectService;
import com.tsheetweb.task.entity.Task;
import com.tsheetweb.task.entity.TaskRepository;
import com.tsheetweb.task.entity.TaskService;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsRepository;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class ContractorService implements UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(ContractorService.class);

    @Autowired
    private ContractorRepository contractorRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ContractorProfileRepository profileRepo;

    @Autowired
    private TaskTotalsService taskTotalsService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private TaskTotalsRepository taskTotalsRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LOGGER.trace("Using method loadUserByUsername");
        LOGGER.debug("Load user by email: ");

        Contractor contractor = contractorRepo.findByEmail(email);
        if (contractor == null){
            LOGGER.error("Input data does not match. This contractor was not found");
            throw new UsernameNotFoundException("Contractor not Found");
        }
        LOGGER.info("Input details matched database query. This contractor is verified");
        LOGGER.warn("Logging data processes");
        return new ContractorSecConfig(contractor);
    }

    public void securePassword(Contractor contractor){
        LOGGER.warn("The original password will be encrypted");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(contractor.getPassword());
        contractor.setPassword(encodedPassword);
        LOGGER.info("Password encryption is successful");
    }

    public Contractor getContractorByEmail(String email){
        return contractorRepo.findByEmail(email);
    }

    public void getContractorData(long id, Model model){
        Contractor contractor = contractorRepo.findById(id).get();
        List<Contractor> contractorProfile = contractorRepo.findContractorID(id);
        List<Project> listProjects = projectRepo.findProjectByContractorID(id);
        List<ContractorProfile> listProfile = profileRepo.findProfileByContractorID(id);

        model.addAttribute("contractor", contractor);
        model.addAttribute("profile", contractorProfile);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listProfile", listProfile);
    }

    public Contractor getContactor(long contractorID){
       return  contractorRepo.findById(contractorID).get();
    }

    public List<Contractor> getContractorListData(long contractorID){
        return contractorRepo.findContractorID(contractorID);
    }

    public void ranIdGen(Contractor contractor){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        contractor.setId(rightNow.getTimeInMillis());
        LOGGER.info("Random UUID generated for new user");
    }


    public Contractor saveInDB(Contractor contractor){
        LOGGER.info("Contractor details saved onto the database");
        return  contractorRepo.save(contractor);
    }

    public void deleteInDB(long id){
        LOGGER.info("Contractor details deleted from the database");
        contractorRepo.deleteById(id);
    }



}
