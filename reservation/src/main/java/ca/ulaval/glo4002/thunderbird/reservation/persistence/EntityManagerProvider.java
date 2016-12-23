package ca.ulaval.glo4002.thunderbird.reservation.persistence;

import javax.persistence.EntityManager;

public class EntityManagerProvider {
    private static ThreadLocal<EntityManager> localEntityManager = new ThreadLocal<>();

    public static void clearEntityManager() {
        localEntityManager.set(null);
    }

    public EntityManager getEntityManager() {
        return localEntityManager.get();
    }

    public static void setEntityManager(EntityManager entityManager) {
        localEntityManager.set(entityManager);
    }

    public void executeInTransaction(Runnable transaction) {
        localEntityManager.get().getTransaction().begin();
        transaction.run();
        localEntityManager.get().getTransaction().commit();
    }

    public void persistInTransaction(Object object) {
        executeInTransaction(() -> localEntityManager.get().persist(object));
    }
}