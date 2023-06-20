package com.tsheetweb.task.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    @Query("SELECT t FROM Task t WHERE t.project.p_id= :projectId")
    List<Task> findTaskByProjectID(long projectId);

    @Query("SELECT t FROM Task t WHERE t.weekOf= :weekOf AND t.project.p_id= :projectId")
    List<Task> findTaskByWeekOf(Date weekOf, long projectId);

    @Query("SELECT t FROM Task t WHERE t.taskTotals.tt_id= :totalsId")
    List<Task> findTaskByTaskTotalsID(long totalsId);

    @Query("SELECT t FROM Task t WHERE t.t_id= :taskId")
    Task findTaskByTaskID(long taskId);


}
