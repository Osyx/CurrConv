package main.java.model;

import javax.persistence.NoResultException;

public class CurrencyError extends Exception {
    private String msg;
    private Exception exception;

    public CurrencyError(String s) {
        this.msg = s;
    }

    public CurrencyError(String s, Exception e) {
        this.msg = s;
        this.exception = e;
    }
}
