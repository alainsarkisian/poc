package com.alain.service;

import com.alain.model.Intern;
import com.alain.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedisService {

    @Autowired
    private RedisRepository redisRepository;

    public Optional<Intern> getAnInternById(Long id){
        return this.redisRepository.findById(id);
    }
}
