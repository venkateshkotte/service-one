package com.centime.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class PersonName {
    @NotBlank(message = "name should not be empty")
    private String name;
    @NotBlank(message = "surname should not be empty")
    private String surname;
}
