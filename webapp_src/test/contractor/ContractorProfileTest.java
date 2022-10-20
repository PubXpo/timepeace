package com.tsheetweb.contractor.profile;

import com.tsheetweb.contractor.entity.Contractor;
import org.junit.jupiter.api.*;
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
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class ContractorProfileTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ContractorProfileRepository contractorProfileRepo;


    @Test
    @DisplayName("Expecting Success: Create a new contractor profile instance in DB")
    @Rollback(value = false)
    @Order(1)
    public void testCreateContractorProfileExist_No() {

        Long c_id = 1659462933231L;
        Contractor c = entityManager.find(Contractor.class,c_id);


        //given
            ContractorProfile cp = new ContractorProfile();

            //generate ranID
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(new Date());
            cp.setCp_id(rightNow.getTimeInMillis());
            cp.setContractor(c);
            cp.setCompanyName("test");
            cp.setAddressFirstLine("test");
            cp.setAddressSecondLine("test");
            cp.setAddressPostcode("test");
            cp.setCity("test");
            cp.setPhoneNumber("123456789");

            ContractorProfile savedContractorProfile = contractorProfileRepo.save(cp);

       // when

        //then
        assertNotNull(savedContractorProfile);

    }

    @Test
    @DisplayName("Expecting Failure: Create a duplicate contractor profile instance in DB")
    @Rollback(value = false)
    @Order(2)
    public void testCreateContractorProfileExist_Yes() {

        Long c_id = 1659462933231L;
        Contractor c = entityManager.find(Contractor.class,c_id);


        //given
        ContractorProfile cp = new ContractorProfile();

        //generate ranID
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        cp.setCp_id(rightNow.getTimeInMillis());
        cp.setContractor(c);
        cp.setCompanyName("test");
        cp.setAddressFirstLine("test");
        cp.setAddressSecondLine("test");
        cp.setAddressPostcode("test");
        cp.setCity("test");
        cp.setPhoneNumber("123456789");

        ContractorProfile savedContractorProfile = contractorProfileRepo.save(cp);

        // when

        //then
        assertNotNull(savedContractorProfile);

    }

    @Test
    @Order(3)
    @DisplayName("Expecting Success: Read an existing contractor profile list from DB")
    public void testFindListByContractorID(){

        //given
        long c_id = 1659462933231L;

        //when
        List<ContractorProfile> contractorProfileList = contractorProfileRepo.findProfileByContractorID(c_id);

        //then
        assertThat(contractorProfileList).size().isGreaterThan(0);
    }

    @Test
    @Order(4)
    @DisplayName("Expecting Failure: Read a non existing contractor profile list from DB")
    public void testFindListByContractorIDExist_no(){

        //given
        long c_id = 1659461L;

        //when
        List<ContractorProfile> contractorProfileList = contractorProfileRepo.findProfileByContractorID(c_id);

        //then
        assertThat(contractorProfileList).size().isGreaterThan(0);
    }


    @Test
    @Order(5)
    @DisplayName("Expecting Success: Read an existing contractor profile instance from DB")
    public void testFindByContractorID_Exist_Yes() {

        //given
        long c_id = 1659462933231L;

        //when
        ContractorProfile contractorProfile = contractorProfileRepo.findContractorProfileByContractorID(c_id);

        //then
        assertThat(contractorProfile.getContractor().getId()).isEqualTo(c_id);
    }


    @Test
    @Order(6)
    @DisplayName("Expecting Failure: Read a non-existing contractor profile instance from DB")
    public void testFindByContractorID_Exist_No() {

        //given
        long c_id = 16594621L;

        //when
        ContractorProfile contractorProfile = contractorProfileRepo.findContractorProfileByContractorID(c_id);

        //then
        assertThat(contractorProfile.getContractor().getId()).isEqualTo(c_id);
    }

    @Test
    @Order(7)
    @DisplayName("Expecting Success: Update contractor profile instance in DB")
    public void testUpdateContractorExist_Yes(){

        //given
        long c_id = 1659462933231L;

        //when
        ContractorProfile cp = contractorProfileRepo.findContractorProfileByContractorID(c_id);
        cp.setCompanyName("Lee Co.");
        cp.setAddressFirstLine("321 Bar Foo");
        cp.setAddressSecondLine("12 Foo");
        cp.setAddressPostcode("B31 F13");
        cp.setCity("FooLand");
        cp.setPhoneNumber("+1 (234) 567-8900");
        contractorProfileRepo.save(cp);

        //when
        ContractorProfile updatedCp = contractorProfileRepo.findContractorProfileByContractorID(c_id);

        //then
        assertThat(cp.getAddressFirstLine()).isEqualTo("321 Bar Foo");
    }

    @Test
    @Order(8)
    @DisplayName("Expecting Failure: Update a non existing contractor profile instance in DB")
    public void testUpdateContractorExist_No(){

        //given
        long c_id = 165946291L;

        //when
        ContractorProfile cp = contractorProfileRepo.findContractorProfileByContractorID(c_id);
        long theId = cp.getCp_id();

        cp.setCompanyName("Lee Co.");
        cp.setAddressFirstLine("321 Bar Foo");
        cp.setAddressSecondLine("12 Foo");
        cp.setAddressPostcode("B31 F13");
        cp.setCity("FooLand");
        cp.setPhoneNumber("+1 (234) 567-8900");
        contractorProfileRepo.save(cp);

        //when
        ContractorProfile updatedCp = contractorProfileRepo.findContractorProfileByContractorID(c_id);

        //then
        assertThat(cp.getAddressFirstLine()).isEqualTo("321 Bar Foo");
    }

    @Test
    @Order(9)
    @DisplayName("Expecting Success: Delete contractor instance in DB")
    public void testDeleteContractorProfileExist_Yes(){
        //given
        long c_id = 1659462933231L;

        //when
        ContractorProfile cp = contractorProfileRepo.findContractorProfileByContractorID(c_id);
        boolean exist_Yes = contractorProfileRepo.findById(cp.getCp_id()).isPresent();

        //when
        contractorProfileRepo.deleteById(cp.getCp_id());

        boolean exist_No = contractorProfileRepo.findById(cp.getCp_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }

    @Test
    @Order(10)
    @DisplayName("Expecting Failure: Delete contractor instance in DB")
    public void testDeleteContractorProfileExist_No(){
        //given
        long c_id = 1659462933231L;

        //when
        ContractorProfile cp = contractorProfileRepo.findContractorProfileByContractorID(c_id);
        boolean exist_Yes = contractorProfileRepo.findById(cp.getCp_id()).isPresent();

        contractorProfileRepo.deleteById(cp.getCp_id());
        boolean exist_No = contractorProfileRepo.findById(cp.getCp_id()).isPresent();

        //then
        assertTrue(exist_Yes);
        assertFalse(exist_No);

    }



}