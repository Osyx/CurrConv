package main.java.integration;

import javax.persistence.*;

public class SomeDAO {
    private final EntityManagerFactory factory;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public SomeDAO() {
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

    private void commitTransaction() {
        try {
            entityManagerThreadLocal.get().getTransaction().commit();
        } catch(RollbackException e) {

        }
    }
}
