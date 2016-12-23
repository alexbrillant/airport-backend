package ca.ulaval.glo4002.thunderbird.boarding.rest.baggage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterBaggageResponse {
    @JsonProperty("allowed")
    private boolean allowed;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("refusation_reason")
    private String refusationReason;

    private RegisterBaggageResponse(boolean allowed, String refusationReason) {
        this.allowed = allowed;
        this.refusationReason = refusationReason;
    }

    private RegisterBaggageResponse(boolean allowed) {
        this.allowed = allowed;
    }

    public static RegisterBaggageResponse accepted() {
        return new RegisterBaggageResponse(true);
    }

    public static RegisterBaggageResponse refused(String refusationReason) {
        return new RegisterBaggageResponse(false, refusationReason);
    }
}
