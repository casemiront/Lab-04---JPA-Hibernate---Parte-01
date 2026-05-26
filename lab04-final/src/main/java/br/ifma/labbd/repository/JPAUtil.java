package br.ifma.labbd.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Fábrica de EntityManager — padrão singleton.
 * Usa a unidade "lab04PU" definida no persistence.xml.
 */
public class JPAUtil {

    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("lab04PU");

    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public void close() {
        factory.close();
    }
}
