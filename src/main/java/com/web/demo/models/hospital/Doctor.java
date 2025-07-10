package com.web.demo.models.hospital;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DOCTOR_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {

    @Id
    @Column(name = "DOCTOR_ID")
    //@SequenceGenerator(name = "DOCTOR_DETAILS_ID_SEQ", sequenceName = "DOCTOR_DETAILS_ID_SEQ", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCTOR_DETAILS_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int doctorId;
    @Column(name = "DOCTOR_NAME")
    private String doctorName;
    @Column(name = "SPECIALIST")
    private String specialist;
    @Column(name = "EXPERIENCE")
    private String experience;
    @Column(name = "AGE")
    private int age;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    @Column(name = "USER_ID")
    private long userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "ADDR_DOCTOR_ID", referencedColumnName = "DOCTOR_ID")
    private Set<Address> address = new HashSet<>();
}
