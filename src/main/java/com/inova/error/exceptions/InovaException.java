package com.inova.error.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class InovaException extends RuntimeException {

    public InovaException(String message) {
        super(message);
    }
}
