package com.chrischou.exceptions;

public class CurrencyNotFoundException extends OperationFailedException {

    public CurrencyNotFoundException() {
        super("cannot found currency in system.");
    }
}
