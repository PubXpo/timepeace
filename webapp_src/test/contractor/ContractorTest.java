package com.tsheetweb.contractor.entity;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ContractorTest {

    @Autowired
    private ContractorRepository repo;

    @Test
    @DisplayName("Create contractor instance in DB")
    @Rollback(value = false)
    @Order(1)
    public void testCreateContractor() {

        //given
            Contractor contractor = new Contractor();

            //generate ranID
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(new Date());
            contractor.setId(rightNow.getTimeInMillis());

            contractor.setEmail("lee@e.mail");
            contractor.setFirstname("lee");
            contractor.setLastname("Berte");

            // encrypt password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode("theSecretW0rdIs@5");
            contractor.setPassword(encodedPassword);

            Contractor savedContractor = repo.save(contractor);

        //when

        //then
        assertNotNull(savedContractor);

    }


    @Test
    @Order(2)
    @DisplayName("Read an existing contractor instance in DB")
    public void testFindByEmailExist_Yes(){

        //given
        String email = "lee@e.mail";

        //when
        Contractor contractor = repo.findByEmail(email);

        //then
        assertThat(contractor.getEmail()).isEqualTo(email);
    }

    @Test
    @Order(3)
    @DisplayName("Read a non-existing contractor instance in DB")
    public void testFindByEmailExist_No(){

        //given
        String email = "lee.l@e.mail";

        //when
        Contractor contractor = repo.findByEmail(email);

        //then
        assertNull(contractor);
    }

    @Test
    @Order(4)
    @DisplayName("Read contractor list data by ID")
    public void testFindContractorID(){
        //given
        String email = "lee@e.mail";

        Contractor contractor = repo.findByEmail(email);

        //when
        List<Contractor> contractorList = repo.findContractorID(contractor.getId());

        //then
        assertThat(contractorList).size().isGreaterThan(0);
    }

    @Test
    @Order(5)
    @DisplayName("Update contractor instance in DB")
    public void testUpdateContractor(){

        //given
        String nickName = "Lee";
        String email = "lee@e.mail";
        Contractor contractor = repo.findByEmail(email);
        contractor.setFirstname(nickName);
        repo.save(contractor);

        //when
        Contractor updatedContractor = repo.findByEmail(email);

        //then
        assertThat(contractor.getFirstname()).isEqualTo(nickName);
    }

    @Test
    @Order(6)
    @Disabled
    @DisplayName("Delete contractor instance in DB")
    public void testDeleteContractor(){
        //given
        String email = "lee@e.mail";
        Contractor contractor = repo.findByEmail(email);
        boolean exist_Yes = repo.findById(contractor.getId()).isPresent();

        //when
        repo.deleteById(contractor.getId());

        boolean exist_No = repo.findById(contractor.getId()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }














}