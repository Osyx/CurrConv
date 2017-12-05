package main.java.controller;

import main.java.integration.CurrencyDAO;
import main.java.model.CurrencyDTO;
import main.java.model.CurrencyError;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Fetcher implements Serializable {
    @EJB
    private CurrencyDAO currencyDB = new CurrencyDAO();

    public CurrencyDTO checkRate(String isoCode) throws CurrencyError {
        return currencyDB.getCurrency(isoCode);
    }
}
