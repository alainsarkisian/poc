package com.alain.repository;

import com.alain.model.Intern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InternsManagementServiceRepositoryTest {
    @Autowired
    private InternsManagementServiceRepository underTest;

    @Test
    void itShouldCheckIfInternExistsFindByFirstName() {
        underTest.deleteAll();
        //given
        String firstName = "santtoo";
        Intern intern = new Intern(0L,firstName,"sarkisian");
        underTest.save(intern);

        //when
        String result = (underTest.findByFirstName(firstName)).getFirstName();

        //then
        assertThat(result).isEqualTo(firstName);
    }
}