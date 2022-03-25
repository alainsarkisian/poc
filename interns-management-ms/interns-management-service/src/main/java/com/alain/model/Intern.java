package com.alain.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Intern")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Intern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIntern")
    private Long idIntern;

    @Column(name= "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

}
