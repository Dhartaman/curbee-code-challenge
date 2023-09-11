package com.carsilva.audit.exception;

import lombok.Getter;

public class AuditKeyNotFoundException extends Exception {

    private static final String EXCEPTION_MESSAGE = "Audit system lacks the necessary information to determine what has changed.";

    @Getter
    private String customMessage;

    public AuditKeyNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }

    public AuditKeyNotFoundException(String customMessage) {
        super(EXCEPTION_MESSAGE);
        this.customMessage = customMessage;
    }

    @Override
    public String toString() {
        return super.toString() + (customMessage != null ? " [Custom Message: " + customMessage + "]" : "");
    }

}
