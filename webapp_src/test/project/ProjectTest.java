package com.tsheetweb.project.entity;

import com.tsheetweb.contractor.entity.Contractor;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@RunWith(Parameterized.class)
class ProjectTest {
    private long testP_id = 0L;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository projectRepo;

    @BeforeEach
    public void setTestP_id(){
        this.testP_id = 1234567899990L;
    }

    @Test
    @DisplayName("Expecting Success: Create a new project instance in DB")
    @Rollback(value = false)
    @Order(1)
    public void testCreateProject_No() throws ParseException {

        Long c_id = 1659462933231L;
        Contractor c = entityManager.find(Contractor.class,c_id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        List<Project> projectList = projectRepo.findProjectByContractorID(c_id);
        boolean projectExist = false;
        boolean created = false;

        //given
        Project p = new Project();

        p.setP_id(testP_id);
        p.setContractor(c);
        p.setProjectNumber("test");
        p.setProjectName("test");
        p.setProjectManager("test");
        p.setStatus("open");
        p.setProgress("50");
        p.setStartDate(formatter.parse("2022-07-31"));
        p.setEndDate(formatter.parse("2022-12-31"));
        p.setRole("Engineer");
        p.setRate(15.6F);

        String newContractorProjectCombination = c_id + p.getProjectNumber();

        for (Project eachProject : projectList){
            String existingContractorProjectCombination  = c_id + eachProject.getProjectNumber();

            if (newContractorProjectCombination.equals(existingContractorProjectCombination)){
                projectExist = true;
                break;
            }
        }

        if (!projectExist) {
            projectRepo.save(p);
            created = true;
        }

        // when

        //then
        assertThat(created).isEqualTo(true);

    }

    @Test
    @DisplayName("Expecting Failure: Create a duplicate project instance in DB")
    @Rollback(value = false)
    @Order(2)
    public void testCreateProject_Yes() throws ParseException {

        Long c_id = 1659462933231L;
        Contractor c = entityManager.find(Contractor.class,c_id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        List<Project> projectList = projectRepo.findProjectByContractorID(c_id);
        boolean projectExist = false;
        boolean created = false;

        //given
        Project p = new Project();

        p.setP_id(testP_id);
        p.setContractor(c);
        p.setProjectNumber("test");
        p.setProjectName("test");
        p.setProjectManager("test");
        p.setStatus("open");
        p.setProgress("50");
        p.setStartDate(formatter.parse("2022-07-31"));
        p.setEndDate(formatter.parse("2022-12-31"));
        p.setRole("Engineer");
        p.setRate(15.6F);

        String newContractorProjectCombination = c_id + p.getProjectNumber();

        for (Project eachProject : projectList){
            String existingContractorProjectCombination  = c_id + eachProject.getProjectNumber();

            if (newContractorProjectCombination.equals(existingContractorProjectCombination)){
                projectExist = true;
                break;
            }
        }

        if (!projectExist) {
            projectRepo.save(p);
            created = true;
        }

        //then
        assertThat(created).isEqualTo(true);
    }

    @Test
    @Order(3)
    @DisplayName("Expecting Success: Read a project list by contractorID in the DB")
    public void testFindListByContractorID(){

        //given
        long c_id = 1659462933231L;

        //when
        List<Project> projectList = projectRepo.findProjectByContractorID(c_id);

        //then
        assertThat(projectList).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("Expecting Failure: Read a project list for a non existing contractorID")
    public void testFindListByContractorIDExist_no(){

        //given
        long c_id = 1659461L;

        //when
        List<Project> projectList = projectRepo.findProjectByContractorID(c_id);

        //then
        assertThat(projectList).size().isGreaterThan(0);
    }

    @Test
    @Order(5)
    @DisplayName("Expecting Success: Read a project list by existing contractorID and open status")
    public void testFindByProjectByContractorAndStatus_ContractorExist_Yes() {

        //given
        long c_id = 1659462933231L;
        String status = "open";

        //when
        List<Project> projectList = projectRepo.findProjectByContractorIDAndStatus(c_id, status);

        //then
        assertThat(projectList).size().isGreaterThan(0);
    }

    @Test
    @Order(6)
    @DisplayName("Expecting Failure: Read a project list by existing contractorID and ended status")
    public void testFindByProjectByContractorAndStatus_ContractorExist_No() {

        //given
        long c_id = 1659462933231L;
        String status = "ended";

        //when
        List<Project> projectList = projectRepo.findProjectByContractorIDAndStatus(c_id, status);

        //then
        assertThat(projectList).size().isGreaterThan(0);
    }

    @Test
    @Order(7)
    @DisplayName("Expecting Success: Read an existing project by ID")
    public void testFindByProjectByIdExist_Yes() {

        //given
        long p_id = testP_id;

        //when
        Project p = projectRepo.findProjectByID(p_id);

        //then
        assertThat(p.getRole().equals("Engineer"));

    }

    @Test
    @Order(8)
    @DisplayName("Expecting Failure: Read a non existing project by ID")
    public void testFindByProjectByIdExist_No() {


        //given
        long p_id = 1653231L;

        //when
        Project p = projectRepo.findProjectByID(p_id);

        //then
        Project project = entityManager.find(Project.class, p.getP_id());
        assertNotNull(p);
    }

    @Test
    @Order(9)
    @DisplayName("Expecting Success: Update project instance in DB")
    public void testUpdateProjectExist_Yes() throws ParseException {

        //given
        long c_id = 1659462933231L;
        long p_id = testP_id;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        List<Project> projectList = projectRepo.findProjectByContractorID(c_id);
        boolean projectExist = false;

//        Project p = entityManager.find(Project.class, 1659544136732L);
        Project p = projectRepo.findProjectByID(testP_id);

//        Project p = entityManager.find(Project.class, p_id);

        p.setProjectNumber("TEST001");
        p.setProjectName("Foo");
        p.setProjectManager("Alice");
        p.setCompanyName("AFoo Ltd.");
        p.setDescription("some foo");
        p.setNotes("no foo bar");
        p.setStatus("open");
        p.setProgress("50");
        p.setRole("Engineer");
        p.setRate(25.6F);

        String newContractorProjectCombination = c_id + p.getProjectNumber();

        for (Project eachProject : projectList){
            String existingContractorProjectCombination  = c_id + eachProject.getProjectNumber();

            if (newContractorProjectCombination.equals(existingContractorProjectCombination)){
                projectExist = true;
                break;
            }
        }

        if (!projectExist) {
            projectRepo.save(p);
        }

        //when
        Project updatedP = projectRepo.findById(p_id).get();

        //then
        assertThat(p.getProjectNumber()).isEqualTo("TEST001");

    }

    @Test
    @Order(10)
    @DisplayName("Expecting Failure: Update a non existing project instance in DB")
    public void testUpdateProjectExist_No(){

        //given
        long c_id = 1659462933231L;
        long p_id = 1659757L;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        List<Project> projectList = projectRepo.findProjectByContractorID(p_id);
        boolean projectExist = false;

        Project p = entityManager.find(Project.class, p_id);

        p.setProjectNumber("TEST001");
        p.setProjectName("Foo");
        p.setProjectManager("Alice");
        p.setCompanyName("AFoo Ltd.");
        p.setDescription("some foo");
        p.setNotes("no foo bar");
        p.setStatus("open");
        p.setProgress("50");
        p.setRole("Engineer");
        p.setRate(25.6F);

        String newContractorProjectCombination = c_id + p.getProjectNumber();

        for (Project eachProject : projectList){
            String existingContractorProjectCombination  = c_id + eachProject.getProjectNumber();

            if (newContractorProjectCombination.equals(existingContractorProjectCombination)){
                projectExist = true;
                break;
            }
        }

        if (!projectExist) {
            projectRepo.save(p);
        }

        //when
        Project updatedP = projectRepo.findById(p_id).get();

        //then
        assertThat(p.getProjectNumber()).isEqualTo("TEST001");
    }

    @Test
    @Disabled
    @Order(11)
    @DisplayName("Expecting Success: Delete contractor instance in DB")
    public void testDeleteProjectExist_Yes(){
        //given
        long p_id = testP_id;

        //when
        Project p = entityManager.find(Project.class, p_id);
        boolean exist_Yes = projectRepo.findById(p.getP_id()).isPresent();

        projectRepo.deleteById(p.getP_id());

        boolean exist_No = projectRepo.findById(p.getP_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }

    @Test
    @Disabled
    @Order(12)
    @DisplayName("Expecting Failure: Delete contractor instance in DB")
    public void testDeleteProjectExist_No(){
        //given
        long p_id = testP_id;

        //when
        Project p = entityManager.find(Project.class, p_id);
        boolean exist_Yes = projectRepo.findById(p.getP_id()).isPresent();

        projectRepo.deleteById(p.getP_id());

        boolean exist_No = projectRepo.findById(p.getP_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }


}