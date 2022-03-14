package com.alain.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Intern {
    @JsonProperty("Id_Inter")
    private int idInter;
    @JsonProperty("First_Name")
    private String firstName;
    @JsonProperty("Last_Name")
    private String lastName;

    public Intern(){}

    public Intern(int idInter, String firstName, String lastName) {
        this.idInter = idInter;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getIdInter() {
        return idInter;
    }

    public void setIdInter(int idInter) {
        this.idInter = idInter;
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
}
