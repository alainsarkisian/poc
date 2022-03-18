package com.alain.repository;

import com.alain.model.Intern;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<Intern,Long> {
}
