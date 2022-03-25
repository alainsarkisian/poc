package com.alain.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Intern {

    @JsonProperty("Id_Inter")
    private int idInter;

    @JsonProperty("First_Name")
    private String firstName;

    @JsonProperty("Last_Name")
    private String lastName;

}
