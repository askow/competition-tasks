package pl.ing.business.dto.onlinegame;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private int numberOfPlayers; // min 1 max 1000
    private int points; // min 1 max 1000000

    public Order(int numberOfPlayers, int points) {
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
        } else if (obj instanceof Order order) {
            return numberOfPlayers == order.numberOfPlayers &&
                    points == order.points;
        } else return false;
    }
}
