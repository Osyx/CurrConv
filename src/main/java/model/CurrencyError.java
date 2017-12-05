package main.java.model;

import javax.persistence.NoResultException;

public class CurrencyError extends Throwable {
    private String msg;
    private Exception exception;

    public CurrencyError(String s, Exception e) {
        this.msg = s;
        this.exception = e;
    }
}
