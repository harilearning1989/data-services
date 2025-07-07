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
@Table(name = "ADMIN_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {

    @Id
    @Column(name = "ADMIN_ID")
    //@SequenceGenerator(name = "PATIENT_DETAILS_ID_SEQ", sequenceName = "PATIENT_DETAILS_ID_SEQ", allocationSize = 1)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PATIENT_DETAILS_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;
    @Column(name = "ADMIN_NAME")
    private String adminName;
    @Column(name = "AGE")
    private int age;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    @Column(name = "USER_ID")
    private long userId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    @JoinColumn(name = "ADDR_ADMIN_ID", referencedColumnName = "ADMIN_ID")
    private Set<Address> address = new HashSet<>();
}
