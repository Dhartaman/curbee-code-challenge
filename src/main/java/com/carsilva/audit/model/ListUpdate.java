package com.carsilva.audit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListUpdate implements ChangeType {

    private String property;
    private List<Object> added;
    private List<Object> removed;

}
