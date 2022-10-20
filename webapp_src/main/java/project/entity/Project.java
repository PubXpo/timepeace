package com.tsheetweb.project.entity;

import com.tsheetweb.contractor.entity.Contractor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Project {
    @Id
    private Long p_id;

    @ManyToOne()
    @JoinColumn(name= "contractorId")
    private Contractor contractor;

    //    project
    @Column(name = "project_name", nullable = true, length = 24)
    private String projectName;

    @Column(name = "project_Manager", nullable = true, length = 24)
    private String projectManager;

    @Column(length = 64, nullable = false)
    private String projectNumber;

    @Column(nullable = true, length = 64)
    private String status;

    @Column(length = 64)
    private String progress;

    private String description;
    private String notes;
    private String role;


    @Temporal(TemporalType.DATE )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date endDate;

    //    billing
    private Float rate;
    private String companyName;


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
