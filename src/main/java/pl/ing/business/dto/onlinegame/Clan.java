package pl.ing.business.dto.onlinegame;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clan {
    private int numberOfPlayers; // min 1 max 1000
    private int points; //min 1 max 1000000

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Clan(@JsonProperty("numberOfPlayers") int numberOfPlayers, @JsonProperty("points") int points) {
        this.numberOfPlayers = numberOfPlayers;
        this.points = points;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof Clan clan) {
            return numberOfPlayers == clan.numberOfPlayers &&
                    points == clan.points;
        }
        else return false;
    }
}
