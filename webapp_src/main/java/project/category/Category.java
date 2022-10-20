package com.tsheetweb.project.category;

import com.tsheetweb.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Category {
    @Id
    private Long cat_id;

    @OneToOne
    @JoinColumn(name= "projectId")
    private Project project;

    @Column(nullable = false, length = 24)
    private String categoryName;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
