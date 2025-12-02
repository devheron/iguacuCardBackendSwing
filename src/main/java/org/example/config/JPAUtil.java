package org.example.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = buildFactory();

    private static EntityManagerFactory buildFactory() {
        return Persistence.createEntityManagerFactory("iguacuPU");
    }

    public static EntityManager getEM() {
        return emf.createEntityManager();
    }
}
