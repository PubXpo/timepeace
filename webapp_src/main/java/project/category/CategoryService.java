package com.tsheetweb.project.category;

import com.tsheetweb.contractor.entity.Contractor;
import com.tsheetweb.contractor.entity.ContractorRepository;
import com.tsheetweb.contractor.entity.ContractorSecConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CategoryService implements UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(CategoryService.class);

    @Autowired
    private ContractorRepository contractorRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Contractor contractor = contractorRepo.findByEmail(email);
        if (contractor == null){
            throw new UsernameNotFoundException("Contractor not Found");
        }
        return new ContractorSecConfig(contractor);
    }

    public void ranIdGen(Category category){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        category.setCat_id(rightNow.getTimeInMillis());
    }

    public List<Category> listCategories(long p_id){
        return categoryRepo.findProfileByProjectID(p_id);
    }

    // Check for duplication
    public boolean checkIfCategoryExist(Long p_id, Category cat) {
        LOGGER.trace("Using method checkIfCategoryExist.");
        LOGGER.debug("Load by project id and category.");
        List<Category> categoryList = categoryRepo.findProfileByProjectID(p_id);
        boolean catExist = false;

        LOGGER.info("Making a string for the current category and project combo");
        String newCategoryProjectCombination = p_id + cat.getCategoryName();

        LOGGER.debug("Looking through the category list for the matching combo");
        for (Category eachCat : categoryList){
            LOGGER.info("Making a string for each category and project combo");
            String existingCategoryProjectCombination  = p_id + eachCat.getCategoryName();

            if (newCategoryProjectCombination.equals(existingCategoryProjectCombination)){
                catExist = true;
                break;
            }
        }
        return catExist;
    }

}
