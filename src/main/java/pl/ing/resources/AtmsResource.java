package pl.ing.resources;

import pl.ing.business.AtmFacade;
import pl.ing.business.dto.ATM;
import pl.ing.business.dto.Task;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Path("atms")
public class AtmsResource {
    @Inject
    AtmFacade atmFacade;
    @Path("calculateOrder")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<ATM> calculateOrder(List<Task> serviceTasks) {
        final List<Task> tasks = Optional.ofNullable(serviceTasks).orElse(Collections.emptyList());
        return atmFacade.calculateOrder(tasks);
    }
}
