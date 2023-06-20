package com.tsheetweb.task.entity;

import com.tsheetweb.contractor.entity.Contractor;
import com.tsheetweb.contractor.entity.ContractorRepository;
import com.tsheetweb.contractor.entity.ContractorService;
import com.tsheetweb.contractor.profile.ContractorProfileRepository;
import com.tsheetweb.project.entity.ProjectRepository;
import com.tsheetweb.project.entity.ProjectService;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsRepository;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsService;
import com.tsheetweb.task.taskTotals.uploads.TaskUpload;
import com.tsheetweb.task.taskTotals.uploads.TaskUploadRepository;
import com.tsheetweb.task.taskTotals.uploads.TaskUploadService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    private static final Logger LOGGER = LogManager.getLogger(TaskController.class);

    @Autowired
    private  TaskRepository taskRepo;

    @Autowired
    private TaskTotalsRepository taskTotalsRepo;

    @Autowired
    private  TaskService taskService;

    @Autowired
    private TaskTotalsService taskTotalsService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ContractorService contractorService;

    @Autowired
    private TaskUploadService taskUploadService;

    @Autowired
    private TaskUploadRepository taskUploadRepo;


    // view
    @GetMapping("/{c_id}/project/{p_id}/task/week_of/{t_wo}")
    public String showDailyView(@PathVariable("c_id") long c_id, @PathVariable("p_id") long p_id, @PathVariable("t_wo") String t_wo, Model model, Task task) throws ParseException, IOException {
        LOGGER.trace("Using method showDailyView.");
        // debug using logger from each methods
        contractorService.getContractorData(c_id,model);
        projectService.getProjectData(p_id,model);
        taskService.taskNav(t_wo, model, p_id);
        List<Task> taskByWeek = taskRepo.findTaskByWeekOf(taskService.parseDate(t_wo), p_id);
        taskService.sendSumOfDaysToDom(taskByWeek,model);
        taskService.sendCountOfDaysToDom(taskByWeek,model);
        TaskTotals taskTotals = taskTotalsRepo.findTaskByWeekOf(taskService.parseDate(t_wo),p_id);
        String state = taskService.getState(taskService.parseDate(t_wo), p_id);

        // send to DOM
        model.addAttribute("getState", state);
        model.addAttribute("currentDate", t_wo);
        model.addAttribute("thisDate", t_wo);
        model.addAttribute("task", new Task());
        model.addAttribute("taskTotals", taskTotals);

        return "/project/daily_view";
    }

    // Create
    @GetMapping("{c_id}/project/{p_id}/task/new")
    public String showNewTaskForm(@PathVariable("c_id") long c_id, @PathVariable("p_id") long p_id, Model model, Task tasks, TaskTotals taskTotals, @Valid Contractor contractor){
        contractorService.getContractorData(c_id,model);
        projectService.getProjectData(p_id,model);
        model.addAttribute("task", new Task());
        model.addAttribute("taskTotals", taskTotals);
        return "/project/daily_view";
    }


}
