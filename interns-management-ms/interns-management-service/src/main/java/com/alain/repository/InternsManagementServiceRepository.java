package com.alain.repository;

import com.alain.model.Intern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.persistence.NamedNativeQuery;

public interface InternsManagementServiceRepository extends JpaRepository<Intern,Long> {

    @Query("SELECT intern FROM Intern intern WHERE intern.firstName = :firstName")
    Intern findByFirstName(String firstName);
}
