package com.tsheetweb.task.taskTotals.totals.entity;

import com.tsheetweb.contractor.entity.Contractor;
import com.tsheetweb.project.entity.Project;
import com.tsheetweb.task.entity.Task;
import com.tsheetweb.task.entity.TaskRepository;
import com.tsheetweb.task.entity.TaskService;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskServiceTest {

    @MockBean
    private TaskRepository taskRepo;

    @InjectMocks
    private TaskService taskService;

    Project p = new Project();
    Contractor c = new Contractor();
    Task t1 = new Task();
    Task t2 = new Task();
    Task t3 = new Task();
    Task t4 = new Task();
    Task t5 = new Task();
    Task t6 = new Task();
    TaskTotals tt = new TaskTotals();

    @BeforeEach
    void contractorMock(){
        c.setId(123L);
        c.setFirstname("Cat");
        c.setLastname("Smith");
        c.setEmail("c.smith@t.est");
        c.setPassword("password123");
    }

    @BeforeEach
    void projectMock() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        p.setP_id(456L);
        p.setContractor(c);
        p.setProjectNumber("test1");
        p.setProjectName("test1");
        p.setProjectManager("test1");
        p.setStatus("open");
        p.setProgress("50");
        p.setStartDate(formatter.parse("2022-07-31"));
        p.setEndDate(formatter.parse("2022-12-31"));
        p.setRole("Admin");
        p.setRate(10.6F);
    }

    @BeforeEach
    void taskListMock() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //t1
        t1.setT_id(135L);
        t1.setProject(p);
        t1.setWeekOf(formatter.parse("2022-07-31"));
        t1.setDescription("Doing some admin work");
        t1.setMonday(2);
        t1.setTuesday(1);
        t1.setWednesday(0);
        t1.setThursday(1);
        t1.setFriday(1);
        t1.setSaturday(2);
        t1.setSunday(2);
        t1.setWeekTotal();
        t1.setStatus("in progress");

        //t2
        t2.setT_id(136L);
        t2.setProject(p);
        t2.setWeekOf(formatter.parse("2022-07-31"));
        t2.setDescription("Doing some more admin work");
        t2.setMonday(1);
        t2.setTuesday(1);
        t2.setWednesday(2);
        t2.setThursday(0);
        t2.setFriday(0);
        t2.setSaturday(0);
        t2.setSunday(2);
        t2.setWeekTotal();
        t2.setStatus("in progress");

    }

    @BeforeEach
    void taskTotalsMock() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        tt.setTt_id(987L);
        tt.setProject(p);

    }

    @Test
    @DisplayName("Expecting Success: Populate null values when creating a new instance")
    @Order(1)
    public void testPopulateTasks(){

        //given
        String originalCat = t1.getCategory();
        Task task = t1;

        //when
        taskService.populateTasks(task);

        System.out.println("==============================================\n");
        System.out.println("old= "+originalCat);
        System.out.println("new= "+task.getCategory());
        System.out.println("\n==============================================");

        //then
        assertNotNull(task.getDescription());
        assertNotEquals(originalCat, task.getCategory());

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of hours worked on Monday")
    @Order(2)
    public void testSumOfMon_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfMon(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 3;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of hours worked on Monday")
    @Order(3)
    public void testSumOfMon_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfMon(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of hours worked on Tuesday")
    @Order(4)
    public void testSumOfTue_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfTue(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 2;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of hours worked on Tuesday")
    @Order(5)
    public void testSumOfTue_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfTue(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of hours worked on Wednesday")
    @Order(6)
    public void testSumOfWed_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfWed(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of hours worked on Wednesday")
    @Order(7)
    public void testSumOfWed_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfWed(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 5;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of hours worked on Thursday")
    @Order(8)
    public void testSumOfThu_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfThu(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 5;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of hours worked on Thursday")
    @Order(9)
    public void testSumOfThu_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfThu(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of hours worked on Friday")
    @Order(10)
    public void testSumOfFri_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfFri(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 2;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of hours worked on Friday")
    @Order(11)
    public void testSumOfMFri_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfFri(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of hours worked on Saturday")
    @Order(12)
    public void testSumOfSat_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfSat(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 2;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of hours worked on Saturday")
    @Order(13)
    public void testSumOfSat_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfSat(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of hours worked on Sunday")
    @Order(14)
    public void testSumOfSun_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfSun(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of hours worked on Sunday")
    @Order(15)
    public void testSumOfSun_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfSun(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 5;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate sum of Totals")
    @Order(16)
    public void testSumOfTotal_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfTotals(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 22;
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate sum of Totals")
    @Order(17)
    public void testSumOfTotal_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer result = 0;

        //when
        Collection<Integer> getSum = taskService.sumOfTotals(taskList);

        for ( Integer i : getSum){
            result = i;
        }

        //then
        Integer expecting = 4;
        assertThat(result).isEqualTo(expecting);

    }


    @Test
    @DisplayName("Expecting Success: Get accurate day of week")
    @Order(18)
    public void testGetWeeksOfKeys_Yes() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date expecting = formatter.parse("2022-07-31");

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Date result = null;

        //when
        Set<Date> getDate = taskService.getWeekOfKeys(taskList);
        for ( Date i : getDate){
            result = i;
        }

        //then
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate Get accurate day of week")
    @Order(19)
    public void testGetWeeksOfKeys() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date expecting = formatter.parse("2022-06-31");

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Date result = null;

        //when
        Set<Date> getDate = taskService.getWeekOfKeys(taskList);
        for ( Date i : getDate){
            result = i;
        }

        //then
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate status")
    @Order(20)
    public void testGetWeeksStatus_Yes() throws ParseException {

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        String result="";

        //when
        List<Task> getStatus = taskService.status(taskList);
        for ( Task i : getStatus){
            result =i.getStatus();
        }

        //then
        String expecting = "in progress";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate status")
    @Order(21)
    public void testGetWeeksStatus_No() throws ParseException {

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        String result="";

        //when
        List<Task> getStatus = taskService.status(taskList);
        for ( Task i : getStatus){
            result =i.getStatus();
        }

        //then
        String expecting = "submitted";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate count of tasks done on Mon")
    @Order(22)
    public void testCountOfMon_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_mon(taskList);

        //then
        String expecting = "2 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate count of tasks done on Mon")
    @Order(23)
    public void testCountOfMon_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_mon(taskList);

        //then
        String expecting = "3 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate count of tasks done on Tue")
    @Order(24)
    public void testCountOfTue_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_tue(taskList);

        //then
        String expecting = "2 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate count of tasks done on Tue")
    @Order(25)
    public void testCountOfTue_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_tue(taskList);

        //then
        String expecting = "3 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate count of tasks done on Wed")
    @Order(26)
    public void testCountOfWed_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_wed(taskList);

        //then
        String expecting = "2 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate count of tasks done on Wed")
    @Order(27)
    public void testCountOfWed_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_wed(taskList);

        //then
        String expecting = "3 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate count of tasks done on Thu")
    @Order(28)
    public void testCountOfThu_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_thu(taskList);

        //then
        String expecting = "3 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate count of tasks done on Thu")
    @Order(29)
    public void testCountOfThu_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_thu(taskList);

        //then
        String expecting = "4 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate count of tasks done on Fri")
    @Order(30)
    public void testCountOfFri_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_fri(taskList);

        //then
        String expecting = "2 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate count of tasks done on Fri")
    @Order(31)
    public void testCountOfFri_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_fri(taskList);

        //then
        String expecting = "3 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate count of tasks done on Sat")
    @Order(32)
    public void testCountOfSat_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_sat(taskList);

        //then
        String expecting = "1 Task";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate count of tasks done on Sat")
    @Order(33)
    public void testCountOfSat_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_sat(taskList);

        //then
        String expecting = "3 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get accurate count of tasks done on Sun")
    @Order(34)
    public void testCountOfSun_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_sun(taskList);

        //then
        String expecting = "2 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Failure: Get accurate count of tasks done on Sun")
    @Order(35)
    public void testCountOfSun_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);

        //when
        String result = taskService.count_sun(taskList);

        //then
        String expecting = "3 Tasks";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Success: Get state of week")
    @Order(36)
    public void testGetState_Yes(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Date theDate = t1.getWeekOf();
        long p_id = p.getP_id();

        //when
        when(taskRepo.findTaskByWeekOf(theDate, p_id)).thenReturn(taskList);
        String result = taskService.getState(theDate, p_id);

        //then
        String expecting = "in progress";
        assertThat(result).isEqualTo(expecting);

    }

    @Test
    @DisplayName("Expecting Fail: Get state of week")
    @Order(37)
    public void testGetState_No(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Date theDate = t1.getWeekOf();
        long p_id = p.getP_id();

        //when
        when(taskRepo.findTaskByWeekOf(theDate, p_id)).thenReturn(taskList);
        String result = taskService.getState(theDate, p_id);

        //then
        String expecting = "submitted";
        assertThat(result).isEqualTo(expecting);

    }


    @Test
    @DisplayName("Expecting Success: Populate task totals values when creating a new task instance")
    @Order(38)
    public void testPopulateTaskTotals(){

        //given
        List<Task> taskList = asList(t1,t2,t3,t4,t5,t6);
        Integer originalWeekTotals = tt.getWeekTotal();
        TaskTotals taskTotals = tt;

        //when
        when(taskRepo.findTaskByTaskTotalsID(taskTotals.getTt_id())).thenReturn(taskList);
        taskService.populateTaskTotals(taskTotals.getTt_id(), taskTotals);

        System.out.println("==============================================\n");
        System.out.println("old= "+originalWeekTotals);
        System.out.println("new= "+taskTotals.getWeekTotal());
        System.out.println("\n==============================================");

        //then
        assertNotNull(taskTotals.getWeekTotal());
        assertNotEquals(originalWeekTotals, taskTotals.getWeekTotal());
    }

    }