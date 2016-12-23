package ca.ulaval.glo4002.thunderbird.boarding.persistence;

import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.contexts.ServiceLocator;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.flight.HibernateFlightRepositoryIntegrationTest;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.passenger.HibernatePassengerRepositoryIntegrationTest;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.HibernatePlaneRepositoryIntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HibernateFlightRepositoryIntegrationTest.class,
        HibernatePassengerRepositoryIntegrationTest.class,
        HibernatePlaneRepositoryIntegrationTest.class
})
public class IntegrationTestSuite {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeClass
    public static void beforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("boarding-test");
        entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);
    }

    @AfterClass
    public static void afterClass() {
        entityManager.close();
        EntityManagerProvider.clearEntityManager();
        entityManagerFactory.close();
        ServiceLocator.reset();
    }
}