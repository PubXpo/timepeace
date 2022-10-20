package com.tsheetweb.task.taskTotals.uploads;

import com.tsheetweb.task.taskTotals.totals.TaskTotals;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TaskUpload {

    @Id
    private Long tu_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "taskTotalsId")
    private TaskTotals taskTotals;

    private String invoice;
    private long invoiceSize;
    private byte [] invoiceContent;

    private String payslip;
    private long payslipSize;
    private byte [] payslipContent;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
