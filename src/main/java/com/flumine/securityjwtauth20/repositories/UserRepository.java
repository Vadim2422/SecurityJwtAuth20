package com.flumine.securityjwtauth20.repositories;

import java.util.Optional;

import com.flumine.securityjwtauth20.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Boolean existsByEmailIgnoreCase(String email);
    Boolean existsByUsernameIgnoreCase(String email);

    Optional<UserModel> findByEmailIgnoreCase(String email);
    Optional<UserModel> findByUsernameIgnoreCase(String username);
    Optional<UserModel> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);
    void deleteByUsername(String username);

}
