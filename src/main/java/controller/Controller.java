package main.java.controller;

import main.java.integration.CurrencyDAO;
import main.java.model.CurrencyDTO;
import main.java.model.CurrencyError;

public class Controller {
    private final CurrencyDAO currencyDAO = new CurrencyDAO();

    public CurrencyDTO checkRate(String isoCode) throws CurrencyError {
        return currencyDAO.getCurrency(isoCode);
    }
}
