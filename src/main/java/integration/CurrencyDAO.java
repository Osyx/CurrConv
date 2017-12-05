package main.java.integration;

import main.java.model.Currency;
import main.java.model.CurrencyDTO;
import main.java.model.CurrencyError;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class CurrencyDAO {
    @PersistenceContext(unitName = "currconvPU")
    private EntityManager entityManager;

    public CurrencyDTO getCurrency(String isoCode) throws CurrencyError {
        Currency currency = entityManager.find(Currency.class, isoCode);
        if(currency == null)
            throw new CurrencyError("No currency named " + isoCode + " exists, maybe you typed the wrong ISO code? It's three letters long.");
        return  currency;
    }
}
