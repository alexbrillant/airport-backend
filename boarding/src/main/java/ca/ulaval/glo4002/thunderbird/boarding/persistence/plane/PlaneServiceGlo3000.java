package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Plane;
import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.PlaneId;
import ca.ulaval.glo4002.thunderbird.boarding.persistence.plane.exceptions.PlaneNotFoundException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class PlaneServiceGlo3000 implements PlaneService {
    private static final String PLANE_SERVICE_LOCATION = "http://glo3000.ift.ulaval.ca/plane-blueprint";
    private static final String PLANE_INFORMATION_PATH_FORMAT = "/planes/%1s.json";
    private static final String SEATS_INFORMATION_PATH_FORMAT = "/planes/%1s/seats.json";

    private final PlaneAssembler planeAssembler;

    public PlaneServiceGlo3000() {
        this.planeAssembler = new PlaneAssembler();
    }

    @Override
    public Plane getPlane(PlaneId planeId) {
        PlaneDTO planeDto = getPlaneDto(planeId);
        SeatsDTO seatsDto = getSeatsDto(planeId);

        return planeAssembler.toDomain(planeDto, seatsDto);
    }

    private PlaneDTO getPlaneDto(PlaneId planeId) {
        String url = PLANE_SERVICE_LOCATION + String.format(PLANE_INFORMATION_PATH_FORMAT, planeId.getModel());
        ClientResponse response = getResource(url);
        verifyResponse(response, planeId);

        return response.getEntity(PlaneDTO.class);
    }

    private SeatsDTO getSeatsDto(PlaneId planeId) {
        String url = PLANE_SERVICE_LOCATION + String.format(SEATS_INFORMATION_PATH_FORMAT, planeId.getModel());
        ClientResponse response = getResource(url);
        verifyResponse(response, planeId);

        return response.getEntity(SeatsDTO.class);
    }

    private ClientResponse getResource(String url) {
        Client client = Client.create();
        WebResource resource = client.resource(url);

        return resource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
    }

    private void verifyResponse(ClientResponse response, PlaneId planeId) {
        if (response.getStatus() == ClientResponse.Status.NOT_FOUND.getStatusCode()) {
            throw new PlaneNotFoundException(planeId);
        } else if (response.getStatus() != ClientResponse.Status.OK.getStatusCode()) {
            throw new RuntimeException("Failed to retrieve JSON with status : " + response.getStatus());
        }
    }
}