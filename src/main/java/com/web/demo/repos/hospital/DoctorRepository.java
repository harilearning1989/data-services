package com.web.demo.repos.hospital;

import com.web.demo.models.hospital.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
