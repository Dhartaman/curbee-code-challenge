package com.carsilva.audit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private String firstName;
    private Subscription subscription;
    private List<Object> services;
    private List<Vehicle> vehicles;

    public User() {
        this.services = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

}
