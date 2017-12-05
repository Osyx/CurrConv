package main.java.integration;

import main.java.model.Currency;
import main.java.model.CurrencyDTO;
import main.java.model.CurrencyError;

import javax.persistence.*;

public class CurrencyDAO {
    private final EntityManagerFactory factory;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public CurrencyDAO() {
        factory = Persistence.createEntityManagerFactory("factory");
    }

    private EntityManager beginTransaction() {
        EntityManager em = factory.createEntityManager();
        entityManagerThreadLocal.set(em);
        EntityTransaction transaction = em.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        return em;
    }

    private void commitTransaction() throws CurrencyError {
        try {
            entityManagerThreadLocal.get().getTransaction().commit();
        } catch(RollbackException e) {
            throw new CurrencyError("Couldn't commit.", e);
        }
    }

    public CurrencyDTO getCurrency(String isoCode) throws CurrencyError {
        EntityManager em = beginTransaction();
        try {
            TypedQuery currency = em.createNamedQuery("selectCurrency", Currency.class);
            currency.setParameter("isoCode", isoCode);
            return (Currency) currency.getSingleResult();
        } catch (NoResultException noSuchCurrency) {
            throw new CurrencyError("No such currency exists, maybe you typed the wrong ISO code? It's three letters long.", noSuchCurrency);
        } finally {
            commitTransaction();
        }
    }
}
