package ca.ulaval.glo4002.thunderbird.boarding.domain.plane;

public interface PlaneRepository {
    Plane getPlane(PlaneId planeId);

    void savePlane(Plane plane);
}
