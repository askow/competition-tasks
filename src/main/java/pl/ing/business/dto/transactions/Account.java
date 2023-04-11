package pl.ing.business.dto.transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private final String account; // min & max length 26
    private final int debitCount;
    private final int creditCount;
    private final float balance;

    public Account(String account, int debitCount, int creditCount, float balance) {
        this.account = account;
        this.debitCount = debitCount;
        this.creditCount = creditCount;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public int getDebitCount() {
        return debitCount;
    }

    public int getCreditCount() {
        return creditCount;
    }

    public float getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Account acc) {
            return account.equals(acc.account) &&
                    balance == acc.balance &&
                    debitCount == acc.debitCount &&
                    creditCount == acc.creditCount;
        } else return false;
    }
}
