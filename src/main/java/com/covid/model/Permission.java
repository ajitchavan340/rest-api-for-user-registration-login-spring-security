package com.covid.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Permission extends BaseIdEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
