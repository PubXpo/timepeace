package com.tsheetweb.task.taskTotals.totals.totals;

import com.tsheetweb.project.entity.Project;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@RunWith(Parameterized.class)
class TaskTotalsTest {
    private long testP_id = 0L;
    private long testT_id1 = 0L;
    private long testTT_id = 0L;


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskTotalsRepository taskTotalsRepo;

    @BeforeEach
    public void setTestP_id(){
        this.testP_id = 1234567899990L;
    }

    @BeforeEach
    public void setTestT_id1(){
        this.testT_id1 = 3214567899990L;
    }

    @BeforeEach
    public void setTestTT_id(){
        this.testTT_id = 4214567899990L;
    }

    @Test
    @DisplayName("Expecting Success: Create a Task Totals instance in DB")
    @Order(1)
    public void testCreateTaskTotals() throws ParseException {

        Project p = entityManager.find(Project.class,testP_id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //given
        TaskTotals taskTotals = new TaskTotals();

        taskTotals.setTt_id(testTT_id);
        taskTotals.setWeekOf(formatter.parse("2022-07-31"));
        taskTotals.setProject(p);
        taskTotals.setWeekTotal(12);
        taskTotals.setSumOfMonday(10);
        taskTotals.setSumOfTuesday(10);
        taskTotals.setSumOfWednesday(16);
        taskTotals.setSumOfThursday(12);
        taskTotals.setSumOfFriday(12);
        taskTotals.setSumOfSaturday(12);
        taskTotals.setSumOfSunday(12);
        taskTotals.setWeekTotal(100);
        taskTotals.setStatus("in progress");

        // when
        TaskTotals savedTaskTotals = taskTotalsRepo.save(taskTotals);
        TaskTotals existingTaskTotals = entityManager.find(TaskTotals.class, savedTaskTotals.getTt_id());

        //then
        assertThat(taskTotals.getTt_id()).isEqualTo(existingTaskTotals.getTt_id());

    }

    @Test
    @Order(2)
    @DisplayName("Expecting Success: Read a task totals list by projectID")
    public void testFindListByProjectIDExist_Yes(){

        //given
        long p_id = testP_id;

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByProjectID(p_id);

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
    @Order(3)
    @DisplayName("Expecting Failure: Read a task list by non existing projectId in the DB")
    public void testFindListByProjectIDExist_No(){

        //given
        long p_id = 16594621L;

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByProjectID(p_id);

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("Expecting Success: Read a task totals list by projectID")
    public void testFindListByWeekOfProjectIDExist_Yes() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //given
        long p_id = testP_id;
        Date weekOf = formatter.parse("2022-07-31");

        //when
        TaskTotals taskTotals = taskTotalsRepo.findTaskByWeekOf(weekOf, p_id);

        //then
        TaskTotals existingTaskTotals = entityManager.find(TaskTotals.class, taskTotals.getTt_id());
        assertThat(taskTotals.getTt_id()).isEqualTo(existingTaskTotals.getTt_id());
    }

    @Test
    @Order(5)
    @DisplayName("Expecting Failure: Read a task total by non existing projectId in the DB")
    public void testFindListByWeekOfProjectIDExist_No() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //given
        long p_id = 16594621L;
        Date weekOf = formatter.parse("2022-07-31");

        //when
        TaskTotals taskTotals = taskTotalsRepo.findTaskByWeekOf(weekOf, p_id);

        //then
        TaskTotals existingTaskTotals = entityManager.find(TaskTotals.class, taskTotals.getTt_id());
        assertThat(taskTotals.getTt_id()).isEqualTo(existingTaskTotals.getTt_id());
    }

    @Test
    @Order(6)
    @DisplayName("Expecting Success: Read a task totals list by ContractorID")
    public void testFindListByContractorIDExist_Yes(){

        //given
        long p_id = testP_id;
        Project p = entityManager.find(Project.class,p_id);

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByContractorID(p.getContractor().getId());

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
    @Order(7)
    @DisplayName("Expecting Failure: Read a task list by non existing ContractorID")
    public void testFindListByContractorIDExist_No(){

        //given
        long p_id = 16594621L;
        Project p = entityManager.find(Project.class,p_id);

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByContractorID(p.getContractor().getId());

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
    @Order(8)
    @DisplayName("Expecting Success: Read a task totals list by ContractorID and State")
    public void testFindListByStateAndContractorIDExist_Yes(){

        //given
        long p_id = testP_id;
        String status = "in progress";

        Project p = entityManager.find(Project.class,p_id);

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByState(status, p.getContractor().getId());

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
    @Order(9)
    @DisplayName("Expecting Failure: Read a task totals list by ContractorID and State")
    public void testFindListByStateAndContractorIDExist_No(){

        //given
        long p_id = 16594621L;
        String status = "in progress";

        Project p = entityManager.find(Project.class,p_id);

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByState(status, p.getContractor().getId());

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
    @Order(10)
    @DisplayName("Expecting Success: Read a task totals list by month and year")
    public void testFindListByMonthAndYearExist_Yes(){

        //given
        int month = 7;
        int year = 2022;

        long p_id = testP_id;
        Project p = entityManager.find(Project.class,p_id);
        long c_id = p.getContractor().getId();

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByMonth(month,year, c_id);

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
    @Order(11)
    @DisplayName("Expecting Failure: Read a task totals list by non existing month and year")
    public void testFindListByMonthAndYearExist_No(){

        //given
        int month = 1;
        int year = 2020;

        long p_id = testP_id;
        Project p = entityManager.find(Project.class,p_id);
        long c_id = p.getContractor().getId();

        //when
        List<TaskTotals> taskTotalsList = taskTotalsRepo.findTaskTotalsByMonth(month,year, c_id);

        //then
        assertThat(taskTotalsList).size().isGreaterThan(0);
    }

    @Test
//    @Disabled("disabled while testing other classes")
    @Order(12)
    @DisplayName("Expecting Success: Delete Task Totals instance in DB")
    public void testDeleteTask2Exist_Yes(){
        //given
        long tt_id = testTT_id;

        //when
        TaskTotals tt = entityManager.find(TaskTotals.class, tt_id);
        boolean exist_Yes = taskTotalsRepo.findById(tt.getTt_id()).isPresent();

        taskTotalsRepo.deleteById(tt.getTt_id());

        boolean exist_No = taskTotalsRepo.findById(tt.getTt_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }

    @Test
//    @Disabled("disabled while testing other classes")
    @Order(13)
    @DisplayName("Expecting Failure: Delete Task Totals instance in DB")
    public void testDeleteTask2Exist_No(){
        //given
        long tt_id = testTT_id;

        //when
        TaskTotals tt = entityManager.find(TaskTotals.class, tt_id);
        boolean exist_Yes = taskTotalsRepo.findById(tt.getTt_id()).isPresent();

        taskTotalsRepo.deleteById(tt.getTt_id());

        boolean exist_No = taskTotalsRepo.findById(tt.getTt_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }





}