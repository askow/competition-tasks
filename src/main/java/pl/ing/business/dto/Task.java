package pl.ing.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private final int region; // min 1 max 9999
    private final RequestType requestType;
    private final int atmId; // min 1 max 9999

    public Task(int region, RequestType requestType, int atmId) {
        this.region = region;
        this.requestType = requestType;
        this.atmId = atmId;
    }

    public int getRegion() {
        return region;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public int getAtmId() {
        return atmId;
    }

    @JsonIgnore
    public ATM getATM() {
        return new ATM(this.region, this.atmId);
    }
}
