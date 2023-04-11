package pl.ing.business.dto.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    private String debitAccount; // min & max length = 26

    private String creditAccount; // min & max length = 26
    private float amount;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Transaction(@JsonProperty("debitAccount") String debitAccount,
                       @JsonProperty("creditAccount") String creditAccount,
                       @JsonProperty("amount") float amount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public float getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Transaction trans) {
            return debitAccount.equals(trans.debitAccount) &&
                    creditAccount.equals(trans.creditAccount) &&
                    amount == trans.amount;
        } else return false;
    }
}
