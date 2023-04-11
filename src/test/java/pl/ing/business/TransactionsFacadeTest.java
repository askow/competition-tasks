package pl.ing.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.ing.business.dto.transactions.Account;
import pl.ing.business.dto.transactions.Transaction;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TransactionsFacadeTest {
    private TransactionsFacade transactionsFacade;

    @BeforeEach
    void setUp() {
        transactionsFacade = new TransactionsFacade();
    }

    @Test
    void sample1() {
        List<Transaction> transactions = List.of(
                new Transaction("32309111922661937852684864", "06105023389842834748547303", 10.90f),
                new Transaction("31074318698137062235845814", "66105036543749403346524547", 200.90f),
                new Transaction("66105036543749403346524547", "32309111922661937852684864", 50.10f)
        );

        List<Account> accounts = transactionsFacade.calculateReport(transactions);

        assertThat(accounts).containsExactly(
                new Account("06105023389842834748547303", 0, 1, 10.90f),
                new Account("31074318698137062235845814", 1, 0, -200.90f),
                new Account("32309111922661937852684864", 1, 1, 39.20f),
                new Account("66105036543749403346524547", 1, 1, 150.80f)
        );
    }
}
