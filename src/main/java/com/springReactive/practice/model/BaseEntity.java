package com.springReactive.practice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    public BaseEntity(){super();}
}
