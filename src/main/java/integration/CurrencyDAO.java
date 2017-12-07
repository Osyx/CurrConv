package integration;

import model.Currency;
import model.CurrencyDTO;
import model.CurrencyError;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class CurrencyDAO {
    @PersistenceContext(unitName = "currconvPU")
    private EntityManager entityManager;

    public List<CurrencyDTO> getNewCurrency(String toIsoCode, String fromIsoCode) throws CurrencyError {
        Currency toCurrency = entityManager.find(Currency.class, toIsoCode);
        Currency fromCurrency = entityManager.find(Currency.class, fromIsoCode);
        if(toCurrency == null || fromCurrency == null)
            throw new CurrencyError("One of the currencies named " + toIsoCode + " or " + fromIsoCode + "exists, maybe you typed the wrong ISO code? It's three letters long.");
        List<CurrencyDTO> list = new ArrayList<>();
        list.add(toCurrency);
        list.add(fromCurrency);
        return list;
    }
}
