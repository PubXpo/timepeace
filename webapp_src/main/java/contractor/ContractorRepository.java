package com.tsheetweb.contractor.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    @Query("SELECT c FROM Contractor c WHERE c.email= :email")
    Contractor findByEmail(String email);

    @Query("SELECT c FROM Contractor c WHERE c.id= :contractorID")
    List<Contractor> findContractorID(long contractorID);

}