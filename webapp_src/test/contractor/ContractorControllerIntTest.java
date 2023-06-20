package com.tsheetweb.contractor.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(ContractorController.class)
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContractorControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContractorService contractorService;


    @MockBean
    private Model model;

    @InjectMocks
    private ContractorController contractorController;

    Contractor c = new Contractor();

    @BeforeEach
    void contractorMock(){
        c.setId(123L);
        c.setFirstname("Cat");
        c.setLastname("Smith");
        c.setEmail("c.smith@t.est");
        c.setPassword("password123");
    }



    @Test
    @DisplayName("Expecting Success: Return landing page with new Contractor Attribute")
    @Order(1)
    public void shouldReturnHomePage_Yes() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("contractor"));
    }

    @Test
    @DisplayName("Expecting Failure: Return landing page with new Contractor Attribute")
    @Order(2)
    public void shouldReturnHomePage_No() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("indexes"))
                .andExpect(model().attributeExists("contractors"));
    }

    @Test
    @DisplayName("Expecting Success: Return login page")
    @Order(3)
    public void shouldReturnLoginPage_Yes() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @DisplayName("Expecting Failure: Return login page")
    @Order(4)
    public void shouldReturnLoginPage_No() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("logins"));
    }

    @Test
    @DisplayName("Expecting Success: Return registration page")
    @Order(5)
    public void shouldReturnRegPage_Yes() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contractor/registration"))
                .andExpect(model().attributeExists("contractors"));;
    }

    @Test
    @DisplayName("Expecting Failure:  Return registration page")
    @Order(6)
    public void shouldReturnRegPage_No() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contractor/register"))
                .andExpect(model().attributeExists("contractors"));;
    }


    @Test
    @DisplayName("Expecting Success: Process Registration")
    @Order(7)
    public void shouldProcessRegister_Yes() throws Exception {
        when(contractorService.saveInDB(any(Contractor.class))).thenReturn(c);
        mockMvc.perform(post("/process_register")
                        .content(new ObjectMapper().writeValueAsString(c))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(view().name("registration_success"));
    }

    @Test
    @DisplayName("Expecting Success: Return all attributes in Dashboard page")
    @Order(8)
    public void shouldReturnDashPage_Yes() throws Exception {

        long c_id = c.getId();
        when(contractorService.getContactor(c.getId())).thenReturn(c);
        when(contractorService.getContractorListData(c.getId())).thenReturn(List.of(c));
        mockMvc.perform(get("/"+c_id+"/contractor"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contractor/dashboard"))
                .andExpect(model().attributeExists("contractor"))
                .andExpect(model().attributeExists("profile"))
                .andExpect(model().attributeExists("listProfile"))
                .andExpect(model().attributeExists("listProjects")) ;
    }

    @Test
    @DisplayName("Expecting Failure: Return all attributes in Dashboard page")
    @Order(9)
    public void shouldReturnDashPage_No() throws Exception {

        long c_id = c.getId();
        when(contractorService.getContactor(c.getId())).thenReturn(c);
        when(contractorService.getContractorListData(c.getId())).thenReturn(List.of(c));
        mockMvc.perform(get("/"+c_id+"/contractor"))
                .andExpect(status().isOk())
                .andExpect(view().name("/contractor/dashboard"))
                .andExpect(model().attributeExists("contractor"))
                .andExpect(model().attributeExists("profle"))
                .andExpect(model().attributeExists("listPrfile"))
                .andExpect(model().attributeExists("listProjects")) ;
    }







}