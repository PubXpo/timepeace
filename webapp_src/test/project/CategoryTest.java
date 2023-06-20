package com.tsheetweb.project.category;

import com.tsheetweb.project.entity.Project;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@RunWith(Parameterized.class)
class CategoryTest {
    private long testP_id = 0L;
    private long testCat_id = 0L;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepo;

    @BeforeEach
    public void setTestP_id(){
        this.testP_id = 1234567899990L;
    }

    @BeforeEach
    public void setTestCat_id(){
        this.testCat_id = 1334567899990L;
    }

    @Test
    @Order(1)
    @DisplayName("Expecting Success: Create a new category instance in DB")
    public void testCategoryExist_No() {

        Project p = entityManager.find(Project.class,testP_id);

        List<Category> categoryList = categoryRepo.findProfileByProjectID(testP_id);
        boolean catExist = false;
        boolean created = false;

        //given
        Category cat = new Category();

        cat.setCat_id(testCat_id);
        cat.setProject(p);
        cat.setCategoryName("test");

        String newCategoryProjectCombination = testP_id + cat.getCategoryName();

        for (Category eachCat : categoryList){
            String existingCategoryProjectCombination  = testP_id + eachCat.getCategoryName();

            if (newCategoryProjectCombination.equals(existingCategoryProjectCombination)){
                catExist = true;
                break;
            }
        }

        if (!catExist) {
            categoryRepo.save(cat);
            created = true;
        }

        // when

        //then
        assertThat(created).isEqualTo(true);

    }

    @Test
    @DisplayName("Expecting Failure: Create a duplicate category instance in DB")
    @Order(2)
    public void testCategoryExist_Yes() {

        Project p = entityManager.find(Project.class,testP_id);

        List<Category> categoryList = categoryRepo.findProfileByProjectID(testP_id);
        boolean catExist = false;
        boolean created = false;

        //given
        Category cat = new Category();

        //generate ranID
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        cat.setCat_id(rightNow.getTimeInMillis());
        cat.setProject(p);
        cat.setCategoryName("test");

        String newCategoryProjectCombination = testP_id + cat.getCategoryName();

        for (Category eachCat : categoryList){
            String existingCategoryProjectCombination  = testP_id + eachCat.getCategoryName();

            if (newCategoryProjectCombination.equals(existingCategoryProjectCombination)){
                catExist = true;
                break;
            }
        }

        if (!catExist) {
            categoryRepo.save(cat);
            created = true;
        }

        // when

        //then
        assertThat(created).isEqualTo(true);

    }

    @Test
    @Order(3)
    @DisplayName("Expecting Success: Read an existing category list from DB")
    public void testFindCategoriesByProjectIDExist_Yes(){

        //given
        long p_id = testP_id;

        //when
        List<Category> categoryList = categoryRepo.findProfileByProjectID(p_id);

        //then
        assertThat(categoryList).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("Expecting Failure: Read a non existing category list from DB")
    public void testFindCategoriesByProjectIDExist_No(){

        //given
        long p_id = 1659461L;

        //when
        List<Category> categoryList = categoryRepo.findProfileByProjectID(p_id);

        //then
        assertThat(categoryList).size().isGreaterThan(0);
    }


    @Test
    @Order(5)
    @DisplayName("Expecting Success: Delete category instance in DB")
    public void testDeleteContractorProfileExist_Yes(){
        //given
        long cat_id = testCat_id;

        //when
        Category cat = categoryRepo.findById(cat_id).get();
        boolean exist_Yes = categoryRepo.findById(cat.getCat_id()).isPresent();

        //when
        categoryRepo.deleteById(cat.getCat_id());

        boolean exist_No = categoryRepo.findById(cat.getCat_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }

    @Test
    @Order(6)
    @DisplayName("Expecting Failure: Delete non existing category instance in DB")
    public void testDeleteContractorProfileExist_No(){
        //given
        long cat_id = testCat_id;

        //when
        Category cat = categoryRepo.findById(cat_id).get();
        boolean exist_Yes = categoryRepo.findById(cat.getCat_id()).isPresent();

        //when
        categoryRepo.deleteById(cat.getCat_id());

        boolean exist_No = categoryRepo.findById(cat.getCat_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }




}