package com.tsheetweb.task.taskTotals.uploads;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskUploadRepository extends JpaRepository<TaskUpload, Long> {
    @Query("SELECT tu FROM TaskUpload tu WHERE tu.taskTotals.tt_id= :taskTotalsId")
    TaskUpload findUploadByTaskTotalsId(long taskTotalsId);

}
