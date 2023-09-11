package com.carsilva.audit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyUpdate implements ChangeType {

    private String property;
    private Object previous;
    private Object current;

}
