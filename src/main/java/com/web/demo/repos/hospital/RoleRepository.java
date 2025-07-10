package com.web.demo.repos.hospital;

import com.web.demo.models.hospital.Role;
import com.web.demo.models.hospital.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
