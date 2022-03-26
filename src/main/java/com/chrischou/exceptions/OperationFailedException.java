package com.chrischou.exceptions;

import lombok.Getter;

public class OperationFailedException extends RuntimeException {

    public OperationFailedException(String reason) {
        super(reason);
    }
}
