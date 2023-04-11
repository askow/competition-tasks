package pl.ing.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.ing.business.dto.onlinegame.Clan;
import pl.ing.business.dto.onlinegame.Order;
import pl.ing.business.dto.onlinegame.Players;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class OnlineGameFacadeTest {
    private OnlineGameFacade onlineGameFacade;

    @BeforeEach
    void setUp() {
        onlineGameFacade = new OnlineGameFacade();
    }

    @Test
    void sample1() {
        Players players = new Players(6, List.of(
                new Clan(4, 50),
                new Clan(2, 70),
                new Clan(6, 60),
                new Clan(1, 15),
                new Clan(5, 40),
                new Clan(3, 45),
                new Clan(1, 12),
                new Clan(4, 40)
        ));

        List<List<Order>> orders = onlineGameFacade.calculate(players);

        assertThat(orders).containsExactly(
                List.of(new Order(2, 70), new Order(4, 50)),
                List.of(new Order(6, 60)),
                List.of(new Order(3, 45), new Order(1, 15), new Order(1, 12)),
                List.of(new Order(4, 40)),
                List.of(new Order(5, 40))
        );
    }
}
