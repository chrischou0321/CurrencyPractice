package com.chrischou.exceptions;

public class DuplicateCurrencyException extends OperationFailedException {
    public DuplicateCurrencyException() {
        super("Cannot add duplicate currency by code.");
    }
}
