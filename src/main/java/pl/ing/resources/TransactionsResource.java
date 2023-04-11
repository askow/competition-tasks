package pl.ing.resources;

import pl.ing.business.TransactionsFacade;
import pl.ing.business.dto.transactions.Account;
import pl.ing.business.dto.transactions.Transaction;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Path("transactions")
public class TransactionsResource {
    @Inject
    TransactionsFacade transactionsFacade;

    @Path("report")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> calculateReport(List<Transaction> clientTransactions) {
        List<Transaction> transactions = Optional.ofNullable(clientTransactions).orElse(Collections.emptyList());
        return transactionsFacade.calculateReport(transactions);
    }
}
