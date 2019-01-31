package com.springReactive.practice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class SetBaseEntity {
    private List<BaseEntity> baseEntities;
}
