package com.alain.repository;

import com.alain.model.Intern;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
class InternsManagementServiceRepositoryTest {
    @Autowired
    private InternsManagementServiceRepository underTest;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void itShouldCheckIfInternExistsFindByFirstName() {
        //given
        String firstName = "santtoo";
//        Intern intern = new Intern(null,firstName,"sarkisian");
//        entityManager.persist(intern);
//        entityManager.flush();

        //when
        Optional<Intern> result = (underTest.findByFirstName(firstName));
        boolean b = result.isEmpty();
        //then
        assertThat(b).isTrue();
    }
}