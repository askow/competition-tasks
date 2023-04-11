package pl.ing.business;

import pl.ing.business.dto.transactions.Account;
import pl.ing.business.dto.transactions.Transaction;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class TransactionsFacade {
    public List<Account> calculateReport(List<Transaction> transactions) {
        HashMap<String, BigDecimal> accountToBalance = new HashMap<>();
        HashMap<String, Integer> accountToDebitCount = new HashMap<>();
        HashMap<String, Integer> accountToCreditCount = new HashMap<>();
        transactions.forEach(t -> {
            accountToCreditCount.computeIfAbsent(t.getCreditAccount(), (k) -> 0);
            accountToCreditCount.computeIfPresent(t.getCreditAccount(), (k, v) -> v + 1);

            accountToDebitCount.computeIfAbsent(t.getDebitAccount(), (k) -> 0);
            accountToDebitCount.computeIfPresent(t.getDebitAccount(), (k, v) -> v + 1);

            accountToBalance.computeIfAbsent(t.getCreditAccount(), (k) -> new BigDecimal("0.0"));
            accountToBalance.computeIfPresent(t.getCreditAccount(), (k, v) -> v.add(BigDecimal.valueOf(t.getAmount())));

            accountToBalance.computeIfAbsent(t.getDebitAccount(), (k) -> new BigDecimal("0.0"));
            accountToBalance.computeIfPresent(t.getDebitAccount(), (k, v) -> v.add(BigDecimal.valueOf(-1 * t.getAmount())));
        });

        return accountToBalance.keySet().stream().map(account ->
                        new Account(
                                account,
                                accountToDebitCount.getOrDefault(account, 0),
                                accountToCreditCount.getOrDefault(account, 0),
                                accountToBalance.getOrDefault(account, new BigDecimal("0.0")).floatValue()))
                .toList();
    }
}
