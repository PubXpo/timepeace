package com.tsheetweb.task.taskTotals.totals;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskTotalsRepository extends JpaRepository<TaskTotals, Long> {
    @Query("SELECT tt FROM TaskTotals tt WHERE tt.project.p_id= :projectId ORDER BY tt.weekOf ASC")
    List<TaskTotals> findTaskTotalsByProjectID(long projectId);

    @Query("SELECT t FROM TaskTotals t WHERE t.weekOf= :weekOf AND t.project.p_id= :projectId")
    TaskTotals findTaskByWeekOf(Date weekOf, long projectId);

    @Query("SELECT t FROM TaskTotals t WHERE t.weekOf= :weekOf AND t.project.p_id= :projectId")
    TaskTotals searchTaskByWeekOf(@Param("weekOf") Date weekOf, long projectId);

    @Query("SELECT tt FROM TaskTotals tt WHERE MONTH(tt.weekOf)= :month AND YEAR(tt.weekOf)= :year " +
            "AND tt.project.contractor.id= :contractorId")
    List<TaskTotals> findTaskTotalsByMonth(int month, int year, long contractorId);

    @Query("SELECT tt FROM TaskTotals tt WHERE tt.status= :status " +
            "AND tt.project.contractor.id= :contractorId " +
            "ORDER BY tt.weekOf DESC")
    List<TaskTotals> findTaskTotalsByState(String status, long contractorId);

    @Query("SELECT tt FROM TaskTotals tt WHERE tt.project.contractor.id= :contractorId ORDER BY tt.weekOf ASC")
    List<TaskTotals> findTaskTotalsByContractorID(long contractorId);


}
