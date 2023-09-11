package com.carsilva.audit.model;

import com.carsilva.audit.annotation.AuditKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @AuditKey
    private String vehicleId;
    private String displayName;

}
