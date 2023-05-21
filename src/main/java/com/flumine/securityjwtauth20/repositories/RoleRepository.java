package com.flumine.securityjwtauth20.repositories;

import com.flumine.securityjwtauth20.models.ERole;
import com.flumine.securityjwtauth20.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
        Boolean existsByRole(ERole role);
    Optional<RoleModel> findByRole(ERole role);
}
