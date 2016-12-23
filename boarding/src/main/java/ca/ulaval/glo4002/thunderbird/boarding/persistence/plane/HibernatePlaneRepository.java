package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneRepository;

import javax.persistence.EntityManager;

public class HibernatePlaneRepository implements PlaneRepository {
    private PlaneService planeService;

    public HibernatePlaneRepository(PlaneService planeService) {
        this.planeService = planeService;
    }

    @Override
    public Plane getPlane(PlaneId planeId) {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        EntityManager entityManager = entityManagerProvider.getEntityManager();

        Plane plane = entityManager.find(Plane.class, planeId);
        if (plane == null) {
            plane = planeService.getPlane(planeId);
            entityManagerProvider.persistInTransaction(plane);
        }
        return plane;
    }

    @Override
    public void savePlane(Plane plane) {
        EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
        entityManagerProvider.persistInTransaction(plane);
    }
}