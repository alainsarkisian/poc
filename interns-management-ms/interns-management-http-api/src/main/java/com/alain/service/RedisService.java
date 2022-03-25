package com.alain.service;

import com.alain.model.Intern;
import com.alain.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisRepository redisRepository;

    public Optional<Intern> getAnInternById(Long id){
        return this.redisRepository.findById(id);
    }
}
