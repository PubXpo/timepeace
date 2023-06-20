package com.tsheetweb.task.taskTotals.totals.entity;

import com.tsheetweb.project.entity.Project;
import com.tsheetweb.task.entity.Task;
import com.tsheetweb.task.entity.TaskRepository;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
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
class TaskTest {
    private long testP_id = 0L;
    private long testT_id1 = 0L;
    private long testT_id2 = 0L;
    private long testTT_id = 0L;


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepo;

    @BeforeEach
    public void setTestP_id(){
        this.testP_id = 1234567899990L;
    }

    @BeforeEach
    public void setTestT_id1(){
        this.testT_id1 = 3214567899990L;
    }

    @BeforeEach
    public void setTestT_id2(){
        this.testT_id2 = 3210567899990L;
    }

    @BeforeEach
    public void setTestTT_id(){
        this.testT_id1 = 4214567899990L;
    }



    @Test
    @DisplayName("Expecting Success: Create a first Task instance in DB")
    @Order(1)
    public void testCreateTask1() throws ParseException {

        Project p = entityManager.find(Project.class,testP_id);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //given
        Task t = new Task();

        t.setT_id(testT_id1);
        t.setProject(p);
        t.setWeekOf(formatter.parse("2022-07-31"));
        t.setMonday(0);
        t.setTuesday(0);
        t.setWednesday(7);
        t.setThursday(0);
        t.setFriday(0);
        t.setSaturday(0);
        t.setSunday(8);
        t.setWeekTotal();
        t.setStatus("in progress");

        // when
        Task saveTask = taskRepo.save(t);
        Task existingTask = entityManager.find(Task.class, saveTask.getT_id());

        //then
        assertThat(t.getT_id()).isEqualTo(existingTask.getT_id());

    }

    @Test
    @DisplayName("Expecting Success: Create a second Task instance in DB")
    @Order(2)
    public void testCreateTask2() throws ParseException {

        Project p = entityManager.find(Project.class,testP_id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //given
        Task t = new Task();
        t.setT_id(testT_id2);
        t.setProject(p);
        t.setWeekOf(formatter.parse("2022-07-31"));
        t.setMonday(2);
        t.setTuesday(4);
        t.setWednesday(7);
        t.setThursday(5);
        t.setFriday(5);
        t.setSaturday(8);
        t.setSunday(8);
        t.setWeekTotal();
        t.setStatus("in progress");

        // when
        Task saveTask = taskRepo.save(t);
        Task existingTask = entityManager.find(Task.class, saveTask.getT_id());

        //then
        assertThat(t.getT_id()).isEqualTo(existingTask.getT_id());

    }

    @Test
    @Order(3)
    @DisplayName("Expecting Success: Read a task list by projectId in the DB")
    public void testFindListByProjectIDExist_Yes(){

        //given
        long p_id = testP_id;

        //when
        List<Task> taskList = taskRepo.findTaskByProjectID(p_id);

        //then
        assertThat(taskList).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("Expecting Failure: Read a task list by non existing projectId in the DB")
    public void testFindListByProjectIDExist_No(){

        //given
        long p_id = 16594621L;

        //when
        List<Task> taskList = taskRepo.findTaskByProjectID(p_id);

        //then
        assertThat(taskList).size().isGreaterThan(0);
    }

    @Test
    @Order(5)
    @DisplayName("Expecting Success: Read a task list by projectId and Week of")
    public void testFindListByWeekAndProjectIDExist_Yes() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


        //given
        long p_id = testP_id;
        Date weekOf = formatter.parse("2022-07-31");

        //when
        List<Task> taskList = taskRepo.findTaskByWeekOf(weekOf, p_id);

        //then
        assertThat(taskList).size().isGreaterThan(0);
    }

    @Test
    @Order(6)
    @DisplayName("Expecting Failure: Read a task list by non existing projectId and Week of")
    public void testFindListByWeekAndProjectIDExist_No() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //given
        long p_id = testP_id;
        Date weekOf = formatter.parse("2022-07-30");

        //when
        List<Task> taskList = taskRepo.findTaskByWeekOf(weekOf, p_id);

        //then
        assertThat(taskList).size().isGreaterThan(0);
    }

    @Test
    @Order(7)
    @DisplayName("Expecting Success: Read an existing task by ID")
    public void testFindByTaskByIdExist_Yes() throws ParseException {

        //given
        long t_id = testT_id1;

        //when
        Task t = taskRepo.findTaskByTaskID(t_id);

        //then
        Task existingTask = entityManager.find(Task.class, t.getT_id());
        assertThat(t.getT_id()).isEqualTo(existingTask.getT_id());

    }

    @Test
    @Order(8)
    @DisplayName("Expecting Failure: Read a non existing Task by ID")
    public void testFindByTaskByIdExist_No() {


        //given
        long t_id = 1653231L;

        //when
        Task t = taskRepo.findTaskByTaskID(t_id);

        //then
        Task existingTask = entityManager.find(Task.class, t.getT_id());
        assertThat(t.getT_id()).isEqualTo(existingTask.getT_id());

    }

    @Test
    @DisplayName("Expecting Success: Update the first Task instance in DB")
    @Order(9)
    public void testUpdateTask1() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        TaskTotals tt = entityManager.find(TaskTotals.class,testTT_id);
        //given
        Task t = taskRepo.findTaskByTaskID(testT_id1);
        t.setMonday(6);
        t.setTuesday(8);
        t.setWednesday(7);
        t.setThursday(0);
        t.setFriday(0);
        t.setSaturday(2);
        t.setSunday(2);
        t.setWeekTotal();
        t.setStatus("submitted");
        t.setTaskTotals(tt);
        // when
        Task saveTask = taskRepo.save(t);
        Task existingTask = entityManager.find(Task.class, saveTask.getT_id());
        //then
        assertThat(t.getStatus()).isEqualTo(existingTask.getStatus());
    }

    @Test
    @DisplayName("Expecting Success: Update the second Task instance in DB")
    @Order(10)
    public void testUpdateTask2() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        TaskTotals tt = entityManager.find(TaskTotals.class,testTT_id);


        //given
        Task t = taskRepo.findTaskByTaskID(testT_id2);

        t.setMonday(4);
        t.setTuesday(2);
        t.setWednesday(2);
        t.setThursday(8);
        t.setFriday(2);
        t.setSaturday(2);
        t.setSunday(9);
        t.setWeekTotal();
        t.setStatus("paid");
        t.setTaskTotals(tt);

        // when
        Task saveTask = taskRepo.save(t);
        Task existingTask = entityManager.find(Task.class, saveTask.getT_id());
        //then
        assertThat(t.getStatus()).isEqualTo(existingTask.getStatus());
    }

    @Test
    @Disabled("disabled while testing other classes")
    @Order(11)
    @DisplayName("Expecting Success: Delete Task1 instance in DB")
    public void testDeleteTask1Exist_Yes(){
        //given
        long t_id = testT_id1;

        //when
        Task t = entityManager.find(Task.class, t_id);
        boolean exist_Yes = taskRepo.findById(t.getT_id()).isPresent();

        taskRepo.deleteById(t.getT_id());

        boolean exist_No = taskRepo.findById(t.getT_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }

    @Test
    @Disabled("disabled while testing other classes")
    @Order(12)
    @DisplayName("Expecting Failure: Delete Task1 instance in DB")
    public void testDeleteTask1Exist_No(){
        //given
        long t_id = testT_id1;

        //when
        Task t = entityManager.find(Task.class, t_id);
        boolean exist_Yes = taskRepo.findById(t.getT_id()).isPresent();

        taskRepo.deleteById(t.getT_id());

        boolean exist_No = taskRepo.findById(t.getT_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }

    @Test
    @Disabled("disabled while testing other classes")
    @Order(13)
    @DisplayName("Expecting Success: Delete Task2 instance in DB")
    public void testDeleteTask2Exist_Yes(){
        //given
        long t_id = testT_id2;

        //when
        Task t = entityManager.find(Task.class, t_id);
        boolean exist_Yes = taskRepo.findById(t.getT_id()).isPresent();

        taskRepo.deleteById(t.getT_id());

        boolean exist_No = taskRepo.findById(t.getT_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }

    @Test
    @Disabled("disabled while testing other classes")
    @Order(14)
    @DisplayName("Expecting Failure: Delete Task1 instance in DB")
    public void testDeleteTask2Exist_No(){
        //given
        long t_id = testT_id2;

        //when
        Task t = entityManager.find(Task.class, t_id);
        boolean exist_Yes = taskRepo.findById(t.getT_id()).isPresent();

        taskRepo.deleteById(t.getT_id());

        boolean exist_No = taskRepo.findById(t.getT_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }


}