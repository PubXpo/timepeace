package com.tsheetweb.task.taskTotals.totals;

import com.tsheetweb.project.entity.Project;
import com.tsheetweb.task.taskTotals.uploads.TaskUpload;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskTotals  {

    @Id
    private Long tt_id;

    @ManyToOne
    @JoinColumn(name= "projectId")
    private Project project;

    @Temporal(TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date weekOf;

    private Integer sumOfSunday;
    private Integer sumOfMonday;
    private Integer sumOfTuesday;
    private Integer sumOfWednesday;
    private Integer sumOfThursday;
    private Integer sumOfFriday;
    private Integer sumOfSaturday;
    private Integer weekTotal;
    private String status;

    @OneToOne
    @JoinColumn(name= "taskUploadId", unique=true)
    private TaskUpload taskUpload;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
