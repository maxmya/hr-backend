package com.workmotion.hrbackend.infrastructure.database.employee.persistence;


import com.workmotion.hrbackend.core.domain.employee.EmployeeState;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EmployeeState state;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private String age;

    public void setState(EmployeeState state) {
        this.state = state;
    }
}
