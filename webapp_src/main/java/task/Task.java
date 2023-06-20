package com.tsheetweb.task.entity;

import com.tsheetweb.project.entity.Project;
import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Task {
    @Id private Long t_id;

    @ManyToOne
    @JoinColumn(name= "projectId")
    private Project project;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name= "taskTotalsId")
    private TaskTotals taskTotals;

    @Temporal(TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date weekOf;

    private String description;
    private String category;
    private Integer sunday;
    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;
    @Setter(AccessLevel.NONE)  private Integer weekTotal;
    private String status;
    private Boolean invoiceUploaded;
    private Boolean payslipUploaded;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
