package pl.ing.business.dto.onlinegame;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Players {
    private int groupCount; // min 1, max 1000
    private List<Clan> clans; // maxItems 20000

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Players(@JsonProperty("groupCount") int groupCount,
                   @JsonProperty("clans") List<Clan> clans) {
        this.groupCount = groupCount;
        this.clans = clans;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public List<Clan> getClans() {
        return Optional.ofNullable(clans).orElse(Collections.emptyList());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof Players players) {
            return groupCount == players.groupCount &&
                    clans.equals(players.clans);
        }
        else return false;
    }
}
