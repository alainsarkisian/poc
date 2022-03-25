package com.alain.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Intern")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Intern {
    @Id
    private Long idIntern;

    private String firstName;

    private String lastName;
}
