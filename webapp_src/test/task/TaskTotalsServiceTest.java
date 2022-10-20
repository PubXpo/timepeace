package com.tsheetweb.task.taskTotals.totals.totals;

import com.tsheetweb.contractor.entity.Contractor;
import com.tsheetweb.project.entity.Project;
import com.tsheetweb.task.entity.Task;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsRepository;
import com.tsheetweb.task.taskTotals.totals.TaskTotalsService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskTotalsServiceTest {

    @MockBean
    private TaskTotalsRepository taskTotalsRepo;

    @InjectMocks
    private TaskTotalsService taskTotalsService;

    // initialise Stub objects
    Project p = new Project();
    Project p2 = new Project();
    Contractor c = new Contractor();
    Task t1 = new Task();
    Task t2 = new Task();
    Task t3 = new Task();
    Task t4 = new Task();
    Task t5 = new Task();
    Task t6 = new Task();
    TaskTotals tt1 = new TaskTotals();
    TaskTotals tt2 = new TaskTotals();
    TaskTotals tt3 = new TaskTotals();
    TaskTotals tt4 = new TaskTotals();
    TaskTotals tt5 = new TaskTotals();


    @BeforeEach
    // populate stubs
    void contractorStub(){
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

        //p2
        p2.setP_id(789L);
        p2.setContractor(c);
        p2.setProjectNumber("test2");
        p2.setProjectName("test2");
        p2.setProjectManager("test2");
        p2.setStatus("open");
        p2.setProgress("50");
        p2.setStartDate(formatter.parse("2022-08-07"));
        p2.setEndDate(formatter.parse("2022-12-31"));
        p2.setRole("Engineer");
        p2.setRate(15.6F);
    }

    @BeforeEach
    void taskTotalsMock() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //tt1
        tt1.setSumOfMonday(6);
        tt1.setSumOfTuesday(7);
        tt1.setSumOfWednesday(8);
        tt1.setSumOfThursday(8);
        tt1.setSumOfFriday(8);
        tt1.setSumOfSaturday(8);
        tt1.setSumOfSunday(0);
        tt1.setWeekTotal(45);
        tt1.setTt_id(985L);
        tt1.setProject(p);
        tt1.setWeekOf(formatter.parse("2022-07-17"));

        //tt1
        tt2.setSumOfMonday(8);
        tt2.setSumOfTuesday(8);
        tt2.setSumOfWednesday(8);
        tt2.setSumOfThursday(8);
        tt2.setSumOfFriday(8);
        tt2.setSumOfSaturday(8);
        tt2.setSumOfSunday(0);
        tt2.setWeekTotal(48);
        tt2.setTt_id(986L);
        tt2.setProject(p);
        tt2.setWeekOf(formatter.parse("2022-07-24"));

        //tt3
        tt3.setSumOfMonday(8);
        tt3.setSumOfTuesday(8);
        tt3.setSumOfWednesday(8);
        tt3.setSumOfThursday(8);
        tt3.setSumOfFriday(8);
        tt3.setSumOfSaturday(8);
        tt3.setSumOfSunday(0);
        tt3.setWeekTotal(48);
        tt3.setTt_id(987L);
        tt3.setProject(p);
        tt3.setWeekOf(formatter.parse("2022-07-31"));

        //tt3
        tt4.setSumOfMonday(8);
        tt4.setSumOfTuesday(8);
        tt4.setSumOfWednesday(8);
        tt4.setSumOfThursday(8);
        tt4.setSumOfFriday(7);
        tt4.setSumOfSaturday(7);
        tt4.setSumOfSunday(0);
        tt4.setWeekTotal(46);
        tt4.setTt_id(988L);
        tt4.setProject(p2);
        tt4.setWeekOf(formatter.parse("2022-08-07"));

        //tt5
        tt5.setSumOfMonday(8);
        tt5.setSumOfTuesday(8);
        tt5.setSumOfWednesday(8);
        tt5.setSumOfThursday(8);
        tt5.setSumOfFriday(7);
        tt5.setSumOfSaturday(7);
        tt5.setSumOfSunday(0);
        tt5.setWeekTotal(46);
        tt5.setTt_id(989L);
        tt5.setProject(p);
        tt5.setWeekOf(formatter.parse("2022-08-07"));

    }



    @Test
    @DisplayName("Expecting Success: Find by week of")
    @Order(1)
    public void testSearchByWeekOf_Yes() throws ParseException {
       //given
        TaskTotals taskTotals = tt1;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date d = formatter.parse("2022-07-17");

        //when
        when(taskTotalsRepo.searchTaskByWeekOf(taskTotals.getWeekOf(), taskTotals.getProject().getP_id())).thenReturn(tt1);
        TaskTotals result = taskTotalsService.searchTaskByWeekOf(d, taskTotals.getProject().getP_id());

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Expecting Failure: Find by week of")
    @Order(2)
    public void testSearchByWeekOf_No() throws ParseException {
        //given
        TaskTotals taskTotals = tt1;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date d = formatter.parse("2022-09-31");

        //when
        when(taskTotalsRepo.searchTaskByWeekOf(taskTotals.getWeekOf(), taskTotals.getProject().getP_id())).thenReturn(tt1);
        TaskTotals result = taskTotalsService.searchTaskByWeekOf(d, taskTotals.getProject().getP_id());

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Expecting Success: Find by month")
    @Order(3)
    public void testSearchByMonth_Yes() throws ParseException {
        //given
        TaskTotals taskTotals = tt1;
        List<TaskTotals> taskTotalsList = asList(tt1,tt2,tt3,tt4);
//        List<TaskTotals> taskTotalsList = asList(tt1);

        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        long c_id = c.getId();

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear,c_id )).thenReturn(taskTotalsList);
        List<TaskTotals> result = taskTotalsService.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id);

        //then
        assertNotNull(result);
    }


    @Test
    @DisplayName("Expecting Success: Calculate hours worked for month")
    @Order(4)
    public void testHoursWorkedForMonth_Yes() throws ParseException {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);


        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        Integer result = taskTotalsService.hoursWorkedForMonth(getCurrentMonth, getCurrentYear, c_id);
        Integer expecting = 46;

        //then
        assertThat(result).isEqualTo(expecting);
    }
    @Test
    @DisplayName("Expecting Failure: Calculate hours worked for month")
    @Order(5)
    public void testHoursWorkedForMonth_No() throws ParseException {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        Integer result = taskTotalsService.hoursWorkedForMonth(getCurrentMonth, getCurrentYear, c_id);
        Integer expecting = 45;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate income earned for month")
    @Order(6)
    public void testIncomeEarnedForMonth_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);


        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        double result = taskTotalsService.incomeForMonth(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "717.6";

        //then
        assertThat( String.format("%.1f",result)).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate hours worked for month")
    @Order(7)
    public void testIncomeEarnedForMonth_No() throws ParseException {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        double result = taskTotalsService.incomeForMonth(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "48.6";

        //then
        assertThat( String.format("%.1f",result)).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When up by, compare this and last month's hours worked ")
    @Order(8)
    public void testCompareThisMonthsHours_UpBy() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        tt4.setWeekTotal(300);

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsHours(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Up by 113 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When down by, compare this and last month's hours worked ")
    @Order(9)
    public void testCompareThisMonthsHours_DownBy() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsHours(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Down by 67 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When same as, compare this and last month's hours worked ")
    @Order(10)
    public void testCompareThisMonthsHours_SameAs() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        tt4.setWeekTotal(141);

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsHours(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Same as last month";

        //then
        assertThat( result).isEqualTo(expecting);
    }


    @Test
    @DisplayName("Expecting Failure: Compare this and last month's hours worked ")
    @Order(11)
    public void testCompareThisMonthsHours_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsHours(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Up by 11 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When up by, compare this and last month's income earned ")
    @Order(12)
    public void testCompareThisMonthsIncome_UpBy() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        tt4.setWeekTotal(300);

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsIncome(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Up by 213 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When down by, compare this and last month's income earned ")
    @Order(13)
    public void testCompareThisMonthsIncome_DownBy() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsIncome(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Down by 52 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When same as, compare this and last month's income earned ")
    @Order(14)
    public void testCompareThisMonthsIncome_Same() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        tt4.setWeekTotal(141);

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsIncome(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Up by 47 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Compared this and last month's income earned ")
    @Order(15)
    public void testCompareThisMonthsIncome_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareThisMonthsIncome(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Up by 11 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }


    @Test
    @DisplayName("Expecting Success: Count Projects within the month ")
    @Order(16)
    public void testcountProjects_Yes() throws ParseException {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        Integer result = taskTotalsService.countProjects(getCurrentMonth, getCurrentYear, c_id);
        Integer expecting = 2;

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Count Projects within the month ")
    @Order(17)
    public void testcountProjects_No() throws ParseException {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        Integer result = taskTotalsService.countProjects(getCurrentMonth, getCurrentYear, c_id);
        Integer expecting = 1;

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When up by, compare this and last month's projects ")
    @Order(18)
    public void testCompareMonthProject_UpBy() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareMonthProjects(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Up by 100 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When down by, compare this and last month's projects ")
    @Order(19)
    public void testCompareMonthProject_DownBy() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
//        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList();
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareMonthProjects(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Down by 100 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: When same as, compare this and last month's projects")
    @Order(20)
    public void testCompareMonthProject_Same() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareMonthProjects(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Same as last month";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Compare this and last month's projects")
    @Order(21)
    public void testCompareMonthProject_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();
        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        when(taskTotalsRepo.findTaskTotalsByMonth((getCurrentMonth -1), getCurrentYear, c_id )).thenReturn(taskTotalsListMonthLast);
        String result = taskTotalsService.compareMonthProjects(getCurrentMonth, getCurrentYear, c_id);
        String expecting = "Up by 11 %";

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Return Hash map of income for current month")
    @Order(22)
    public void testPlotMonthlyIncome_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        Map<String, Double> result = taskTotalsService.plotMonthlyIncome(getCurrentYear, c_id);
        Map <String, Double> expecting =new LinkedHashMap<>();
        expecting.put("Jan", 0.0);
        expecting.put("Feb",0.0);
        expecting.put("Mar", 0.0);
        expecting.put("Apr", 0.0);
        expecting.put("May", 0.0);
        expecting.put("Jun", 0.0);
        expecting.put("Jul", 0.0);
        expecting.put("Aug", 1205.2000427246094);
        expecting.put("Sep", 0.0);
        expecting.put("Oct", 0.0);
        expecting.put("Nov", 0.0);
        expecting.put("Dec", 0.0);

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Return Hash map of income for current month")
    @Order(23)
    public void testPlotMonthlyIncome_No() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        Map<String, Double> result = taskTotalsService.plotMonthlyIncome(getCurrentYear, c_id);
        Map <String, Double> expecting =new LinkedHashMap<>();
        expecting.put("Jan", 0.0);
        expecting.put("Feb",0.0);
        expecting.put("Mar", 0.0);
        expecting.put("Apr", 0.0);
        expecting.put("May", 0.0);
        expecting.put("Jun", 0.0);
        expecting.put("Jul", 0.0);
        expecting.put("Aug", 0.0);
        expecting.put("Sep", 0.0);
        expecting.put("Oct", 0.0);
        expecting.put("Nov", 0.0);
        expecting.put("Dec", 0.0);

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Return Hash map of hours worked for current month")
    @Order(24)
    public void testPlotMonthlyHours_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthThis);
        Map<String, Integer> result = taskTotalsService.plotMonthlyHours(getCurrentYear, c_id);
        Map <String, Integer> expecting =new LinkedHashMap<>();
        expecting.put("Jan", 0);
        expecting.put("Feb",0);
        expecting.put("Mar", 0);
        expecting.put("Apr", 0);
        expecting.put("May", 0);
        expecting.put("Jun", 0);
        expecting.put("Jul", 0);
        expecting.put("Aug", 92);
        expecting.put("Sep", 0);
        expecting.put("Oct", 0);
        expecting.put("Nov", 0);
        expecting.put("Dec", 0);

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Return Hash map of hours worked for current month")
    @Order(25)
    public void testPlotMonthlyHours_No() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByMonth(getCurrentMonth, getCurrentYear, c_id )).thenReturn(taskTotalsListMonthAll);
        Map<String, Integer> result = taskTotalsService.plotMonthlyHours(getCurrentYear, c_id);
        Map <String, Integer> expecting =new LinkedHashMap<>();
        expecting.put("Jan", 0);
        expecting.put("Feb",0);
        expecting.put("Mar", 0);
        expecting.put("Apr", 0);
        expecting.put("May", 0);
        expecting.put("Jun", 0);
        expecting.put("Jul", 0);
        expecting.put("Aug", 46);
        expecting.put("Sep", 0);
        expecting.put("Oct", 0);
        expecting.put("Nov", 0);
        expecting.put("Dec", 0);

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate the average hours worked on Mon")
    @Order(26)
    public void testAvgHoursOnMon_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnMon(c_id);
        double expecting =8;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate the average hours worked on Mon")
    @Order(27)
    public void testAvgHoursOnMon_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnMon(c_id);
        double expecting =6;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate the average hours worked on Tue")
    @Order(28)
    public void testAvgHoursOnTue_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnTue(c_id);
        double expecting =8;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate the average hours worked on Tue")
    @Order(29)
    public void testAvgHoursOnTue_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnTue(c_id);
        double expecting =6;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate the average hours worked on Wed")
    @Order(30)
    public void testAvgHoursOnWed_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnWed(c_id);
        double expecting =8;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate the average hours worked on Wed")
    @Order(31)
    public void testAvgHoursOnWed_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnWed(c_id);
        double expecting =6;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate the average hours worked on Thu")
    @Order(32)
    public void testAvgHoursOnThu_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnThu(c_id);
        double expecting =8;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate the average hours worked on Thu")
    @Order(33)
    public void testAvgHoursOnThu_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnThu(c_id);
        double expecting =6;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate the average hours worked on Fri")
    @Order(34)
    public void testAvgHoursOnFri_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnFri(c_id);
        double expecting =8;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate the average hours worked on Fri")
    @Order(35)
    public void testAvgHoursOnFri_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnFri(c_id);
        double expecting =6;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate the average hours worked on Sat")
    @Order(36)
    public void testAvgHoursOnSat_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnSat(c_id);
        double expecting =8;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate the average hours worked on Sat")
    @Order(37)
    public void testAvgHoursOnSat_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnSat(c_id);
        double expecting =6;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Calculate the average hours worked on Sun")
    @Order(38)
    public void testAvgHoursOnSun_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnSun(c_id);
        double expecting =0;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Calculate the average hours worked on Sun")
    @Order(39)
    public void testAvgHoursOnSun_No(){
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id )).thenReturn(taskTotalsListMonthAll);
        double result = taskTotalsService.avgHoursOnSun(c_id);
        double expecting =6;

        //then
        assertThat(result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Success: Return Hash map of average hours worked each day")
    @Order(40)
    public void testPlotDailyHours_Yes() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4,tt5);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id)).thenReturn(taskTotalsListMonthAll);
        Map<String, Double> result = taskTotalsService.plotDailyHours(c_id);
        Map <String, Double> expecting =new LinkedHashMap<>();
        expecting.put("Mon", 8.0);
        expecting.put("Tue", 8.0);
        expecting.put("Wed", 8.0);
        expecting.put("Thu", 8.0);
        expecting.put("Fri", 8.0);
        expecting.put("Sat", 8.0);
        expecting.put("Sun", 0.0);

        //then
        assertThat( result).isEqualTo(expecting);
    }

    @Test
    @DisplayName("Expecting Failure: Return Hash map of average hours worked each day")
    @Order(41)
    public void testPlotDailyHours_No() {
        //given
        long c_id = c.getId();
        Integer getCurrentMonth = taskTotalsService.getCurrentMonth();
        Integer getCurrentYear = taskTotalsService.getCurrentYear();

        List<TaskTotals> taskTotalsListMonthAll = asList(tt1,tt2,tt3,tt4,tt5);
        List<TaskTotals> taskTotalsListMonthThis = asList(tt4);
        List<TaskTotals> taskTotalsListMonthLast = asList(tt1,tt2,tt3);

        //when
        when(taskTotalsRepo.findTaskTotalsByContractorID(c_id)).thenReturn(taskTotalsListMonthAll);
        Map<String, Double> result = taskTotalsService.plotDailyHours(c_id);
        Map <String, Double> expecting =new LinkedHashMap<>();
        expecting.put("Mon", 8.0);
        expecting.put("Tue", 8.0);
        expecting.put("Wed", 9.0);
        expecting.put("Thu", 8.0);
        expecting.put("Fri", 8.0);
        expecting.put("Sat", 8.0);
        expecting.put("Sun", 0.0);

        //then
        assertThat( result).isEqualTo(expecting);

    }




}