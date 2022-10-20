package com.tsheetweb.project.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.contractor.id= :contractorID")
    List<Project> findProjectByContractorID(long contractorID);

    @Query("SELECT p FROM Project p WHERE p.contractor.id= :contractorID AND p.status= :status")
    List<Project> findProjectByContractorIDAndStatus(long contractorID, String status);

    @Query("SELECT p FROM Project p WHERE p.p_id= :projectID")
    Project findProjectByID(Long projectID);



}
