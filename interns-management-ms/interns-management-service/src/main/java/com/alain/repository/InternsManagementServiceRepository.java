package com.alain.repository;

import com.alain.model.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface InternsManagementServiceRepository extends JpaRepository<Intern,Long> {

    @Query("SELECT intern FROM Intern intern WHERE intern.firstName = :firstName")
    Optional<Intern> findByFirstName(String firstName);
}
