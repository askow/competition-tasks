package pl.ing.business.dto.atm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private final int region; // min 1 max 9999
    private final RequestType requestType;
    private final int atmId; // min 1 max 9999

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Task(@JsonProperty("region") int region,
                @JsonProperty("requestType") RequestType requestType,
                @JsonProperty("atmId") int atmId) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof Task atm) {
            return region == atm.getRegion() &&
                    atmId == atm.getAtmId() &&
                    requestType.equals(atm.getRequestType());
        }
        else return false;
    }
}
