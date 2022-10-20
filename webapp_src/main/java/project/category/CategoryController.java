package com.tsheetweb.project.category;

import com.tsheetweb.contractor.entity.Contractor;
import com.tsheetweb.project.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CategoryController {
    private static final Logger LOGGER = LogManager.getLogger(CategoryController.class);


    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepo;

    //create
    @PostMapping("/{c_id}/project={p_id}/category/new")
    public String saveCategory(@PathVariable("c_id") long c_id, Project project, Category category, @Valid Contractor contractor, RedirectAttributes ra){
        LOGGER.trace("Using method saveCategory.");
        LOGGER.debug("Load by project and category combination.");
        categoryService.ranIdGen(category);
        category.setProject(project);
        if (!categoryService.checkIfCategoryExist(project.getP_id(), category)){
            categoryRepo.save(category);
            LOGGER.info("New category added to the database for the corresponding project.");
            ra.addFlashAttribute("message","The new category is added.");
        } else {
            LOGGER.error("Input data already exists. Duplicate category entered.");
            ra.addFlashAttribute("message","That category already exists!");
        }
        return "redirect:/"+c_id+"/project/"+project.getP_id()+"/edit";
    }


    //delete
    @GetMapping("/{c_id}/project={p_id}/cat={cat_id}/delete")
    public String deleteProject(@PathVariable("p_id") long p_id, @PathVariable("c_id") long c_id, @PathVariable("cat_id") long cat_id,Model model, @Valid Contractor contractor) {
        categoryRepo.deleteById(cat_id);
        return "redirect:/"+c_id+"/project/"+p_id+"/edit";
    }
}
