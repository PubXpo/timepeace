package com.tsheetweb.project.entity;

import com.tsheetweb.contractor.entity.Contractor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProjectServiceTest {

    @MockBean
    private ProjectRepository projectRepo;

    @InjectMocks
    private ProjectService projectService;

    Contractor c = new Contractor();

    Project p1 = new Project();
    Project p2 = new Project();
    Project p3 = new Project();

    @BeforeEach
    void contractorMock() {
        c.setId(123L);
        c.setFirstname("Cat");
        c.setLastname("Smith");
        c.setEmail("c.smith@t.est");
        c.setPassword("password123");
    }

    @BeforeEach
    void projectListMock() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        //p1
        p1.setP_id(456L);
        p1.setContractor(c);
        p1.setProjectNumber("test1");
        p1.setProjectName("test1");
        p1.setProjectManager("test1");
        p1.setStatus("open");
        p1.setProgress("0");
        p1.setStartDate(formatter.parse("2022-07-31"));
        p1.setEndDate(formatter.parse("2022-12-31"));
        p1.setRole("Admin");
        p1.setRate(10.6F);

        //p2
        p2.setP_id(789L);
        p2.setContractor(c);
        p2.setProjectNumber("test2");
        p2.setProjectName("test2");
        p2.setProjectManager("test2");
        p2.setStatus("open");
        p2.setProgress("50");
        p2.setStartDate(formatter.parse("2022-07-31"));
        p2.setEndDate(formatter.parse("2022-12-31"));
        p2.setRole("Engineer");
        p2.setRate(15.6F);
    }


    @Test
    @DisplayName("Expecting Success: Duplicate Exists")
    @Order(1)
    public void testCheckIfProjectExist_Yes() throws ParseException {
        //given
        long contractorID = c.getId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Project p = new Project();
        p.setP_id(246L);
        p.setContractor(c);
        p.setProjectNumber("test3");
        p.setProjectName("test3");
        p.setProjectManager("test3");
        p.setStatus("open");
        p.setProgress("100");
        p.setStartDate(formatter.parse("2022-07-31"));
        p.setEndDate(formatter.parse("2022-12-31"));
        p.setRole("Dev");
        p.setRate(12.6F);

        //when
        when(projectRepo.findProjectByContractorID(contractorID)).thenReturn(asList(p1,p2,p3));

        //then
        assertTrue(projectService.checkIfProjectExist(c.getId(), p));

    }

    @Test
    @DisplayName("Expecting Failure: Duplicate Exists")
    @Order(2)
    public void testCheckIfProjectExist_No() throws ParseException {
        //given
        long contractorID = c.getId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Project p = new Project();
        p.setP_id(246L);
        p.setContractor(c);
        p.setProjectNumber("test6");
        p.setProjectName("test3");
        p.setProjectManager("test3");
        p.setStatus("open");
        p.setProgress("100");
        p.setStartDate(formatter.parse("2022-07-31"));
        p.setEndDate(formatter.parse("2022-12-31"));
        p.setRole("Dev");
        p.setRate(12.6F);

        //when
        when(projectRepo.findProjectByContractorID(contractorID)).thenReturn(asList(p1,p2,p3));

        //then
        assertTrue(projectService.checkIfProjectExist(c.getId(), p));

    }

    @Test
    @DisplayName("Expecting Success: Update Project Exists")
    @Order(3)
    public void testUpdateProject_Yes() throws ParseException {
        //given
        long p_id = p3.getP_id();
        String originalStatus = p3.getStatus();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Project updatedP = new Project();
        updatedP.setContractor(c);
        updatedP.setProjectNumber("test3");
        updatedP.setProjectName("test3");
        updatedP.setProjectManager("test3");
        updatedP.setStatus("ended");
        updatedP.setProgress("100");
        updatedP.setStartDate(formatter.parse("2022-07-31"));
        updatedP.setEndDate(formatter.parse("2022-12-31"));
        updatedP.setRole("Dev");
        updatedP.setRate(12.6F);

        //when
        when(projectRepo.findById(p_id)).thenReturn(Optional.ofNullable(p3));
        projectService.updateProjectItem(updatedP.getContractor().getId(), p_id, updatedP);

        System.out.println("==============================================\n");
        System.out.println("old= "+originalStatus);
        System.out.println("new= "+p3.getStatus());
        System.out.println("\n==============================================");

        //then
        assertNotEquals(originalStatus, p3.getStatus());

    }

    @Test
    @DisplayName("Expecting Fail: Update Project Exists")
    @Order(4)
    public void testUpdateProject_No() throws ParseException {
        //given
        long p_id = p3.getP_id();
        String originalStatus = p3.getStatus();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Project updatedP = new Project();
        updatedP.setStatus("ended");

        //when
        when(projectRepo.findById(p_id)).thenReturn(Optional.ofNullable(p3));

        System.out.println("==============================================\n");
        System.out.println("old= "+originalStatus);
        System.out.println("new= "+p3.getStatus());
        System.out.println("\n==============================================");

        //then
        assertNotEquals(originalStatus, p3.getStatus());

    }


}