package pl.ing.business.dto.atm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ATM {
    private final int region; // min 1, max 9999
    private final int atmId; // min 1, max 9999

    public ATM(int region, int atmId) {
        this.region = region;
        this.atmId = atmId;
    }

    public int getRegion() {
        return region;
    }

    public int getAtmId() {
        return atmId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof ATM atm) {
            return this.getAtmId() == atm.getAtmId() && this.getRegion() == atm.getRegion();
        }
        else return false;
    }
}
