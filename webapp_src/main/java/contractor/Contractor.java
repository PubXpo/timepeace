package com.tsheetweb.contractor.entity;

//import javax.persistence.*;

import com.tsheetweb.contractor.profile.ContractorProfile;
import com.tsheetweb.project.entity.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contractors")
public class Contractor {
    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", nullable = false, length = 24)
    private String firstname;

    @Column(name = "last_name", nullable = false, length = 24)
    private String lastname;

    @OneToMany(mappedBy = "p_id")
    private List<Project> projects;

    @OneToOne(cascade = CascadeType.ALL)
    private ContractorProfile contractorProfile;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Contractor{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}



