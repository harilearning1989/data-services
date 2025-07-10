package com.web.demo.repos.hospital;

import com.web.demo.models.hospital.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    int countByCreatedDateBetween(Date start, Date end);
}
