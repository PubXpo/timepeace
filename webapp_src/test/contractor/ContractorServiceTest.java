package com.tsheetweb.contractor.entity;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContractorServiceTest {

    @MockBean
    private ContractorRepository contractorRepo;

    @InjectMocks
    private ContractorService contractorService;

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
    @DisplayName("Expecting Success: Find existing Contractor by email")
    @Order(1)
    public void testGetContractorByEmailExist_Yes(){

        //given
        String contractorEmail = "c.smith@t.est";

        //when
        when(contractorRepo.findByEmail(anyString())).thenReturn(c);
        UserDetails existingContractor =  contractorService.loadUserByUsername(contractorEmail);

        //then
        assertNotNull(existingContractor);
        assertEquals(contractorEmail, existingContractor.getUsername());

    }

    @Test
    @DisplayName("Expecting Failure: Find non existing Contractor by email")
    @Order(2)
    public void testGetContractorByEmailExist_No(){
        //given
        String contractorEmail = "b.smith@t.est";

        //when
        when(contractorRepo.findByEmail(anyString())).thenReturn(c);
        UserDetails existingContractor =  contractorService.loadUserByUsername(contractorEmail);

        //then
        assertNotNull(existingContractor);
        assertEquals(contractorEmail, existingContractor.getUsername());
        
    }
    
    @Test
    @DisplayName("Expecting Success: Encrypt the password")
    @Order(3)
    public void testEncryptPassword_Yes(){
        //given
        String contractorEmail = "c.smith@t.est";
        String visiblePassword = c.getPassword();
        String encryptedPassword;

        //when
        when(contractorRepo.findByEmail(anyString())).thenReturn(c);
        Contractor existingContractor =  contractorService.getContractorByEmail(contractorEmail);
        contractorService.securePassword(existingContractor);
        encryptedPassword = existingContractor.getPassword();

        System.out.println("old= "+visiblePassword);
        System.out.println("new= "+encryptedPassword);

        //then
        assertNotNull(existingContractor);
        assertNotEquals(encryptedPassword, visiblePassword);

    }

    @Test
    @DisplayName("Expecting Failure: Encrypt the password")
    @Order(4)
    public void testEncryptPassword_No(){
        //given
        String contractorEmail = "c.smith@t.est";
        String visiblePassword = c.getPassword();
        String encryptedPassword;

        //when
        when(contractorRepo.findByEmail(anyString())).thenReturn(c);
        Contractor existingContractor =  contractorService.getContractorByEmail(contractorEmail);
        encryptedPassword = existingContractor.getPassword();

        System.out.println("old= "+visiblePassword);
        System.out.println("new= "+encryptedPassword);

        //then
        assertNotNull(existingContractor);
        assertNotEquals(encryptedPassword, visiblePassword);

    }

    @Test
    @DisplayName("Expecting Success: Create a random ID the password")
    @Order(5)
    public void testGenerateRandomID_Yes(){
        //given
        String contractorEmail = "c.smith@t.est";
        long originalID = c.getId();
        long newID;

        //when
        when(contractorRepo.findByEmail(anyString())).thenReturn(c);
        Contractor existingContractor =  contractorService.getContractorByEmail(contractorEmail);
        contractorService.ranIdGen(existingContractor);
        newID = existingContractor.getId();

        System.out.println("old= "+originalID);
        System.out.println("new= "+newID);

        //then
        assertNotNull(existingContractor);
        assertNotEquals(originalID, newID);

    }

    @Test
    @DisplayName("Expecting Failure: Create a random ID the password")
    @Order(6)
    public void testGenerateRandomID_No(){
        //given
        String contractorEmail = "c.smith@t.est";
        long originalID = c.getId();
        long newID;

        //when
        when(contractorRepo.findByEmail(anyString())).thenReturn(c);
        Contractor existingContractor =  contractorService.getContractorByEmail(contractorEmail);
        newID = existingContractor.getId();

        System.out.println("old= "+originalID);
        System.out.println("new= "+newID);

        //then
        assertNotNull(existingContractor);
        assertNotEquals(originalID, newID);

    }

    @Test
    @DisplayName("Expecting Success: Test Save In Db")
    @Order(7)
    public void testSaveInDB(){
        //given
        Contractor newC = c;

        //when
        when(contractorRepo.save(newC)).thenReturn(newC);

        System.out.println("==============================================\n");
        System.out.println("new= "+newC);
        System.out.println("\n==============================================");

        //then
        assertEquals(newC, contractorService.saveInDB(newC));
    }


    @Test
    @DisplayName("Expecting Success: Test Delete In Db")
    @Order(8)
    public void testDeleteInDB(){
        //given
        Contractor newC = c;

        //when
        contractorService.deleteInDB(newC.getId());

        //then
        verify(contractorRepo, times(1)).deleteById(newC.getId());
    }



}