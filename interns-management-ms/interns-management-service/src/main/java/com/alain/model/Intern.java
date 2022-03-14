package com.alain.model;

import javax.persistence.*;

@Entity
@Table(name="Intern")
public class Intern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIntern")
    private Long idIntern;

    @Column(name= "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    public Long getIdIntern() {
        return idIntern;
    }

    public void setIdIntern(Long idIntern) {
        this.idIntern = idIntern;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Intern{" +
                "idIntern=" + idIntern +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
