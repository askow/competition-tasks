package pl.ing.resources;

import pl.ing.business.OnlineGameFacade;
import pl.ing.business.dto.onlinegame.Order;
import pl.ing.business.dto.onlinegame.Players;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Path("onlinegame")
public class OnlineGameResource {
    @Inject
    OnlineGameFacade onlineGameFacade;

    @Path("calculate")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<List<Order>> calculate(Players players) {
        return Optional.ofNullable(players)
                .map(p -> onlineGameFacade.calculate(p))
                .orElse(Collections.emptyList());
    }
}
