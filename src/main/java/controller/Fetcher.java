package controller;

import integration.CurrencyDAO;
import model.CurrencyDTO;
import model.CurrencyError;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.Serializable;
import java.util.List;

@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Fetcher implements Serializable {
    @EJB
    private CurrencyDAO currencyDB = new CurrencyDAO();

    public List<CurrencyDTO> checkNewRate(String toIsoCode, String fromIsoCode) throws CurrencyError {
        return currencyDB.getNewCurrency(toIsoCode, fromIsoCode);
    }
}
