package pl.ing.e2e;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TransactionsGenerator {
    static private final Random random = new Random();
    public static void main(String[] args) {
        final int N = 10; // max 100000
        List<GeneratorTransaction> transactions = generateTransactions(N);
        try {
            File file = new File("/home/user/projects/trans_%s.json".formatted(N));
            new ObjectMapper().writeValue(file, transactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<GeneratorTransaction> generateTransactions(int n) {
        return IntStream.rangeClosed(0, n).mapToObj((i) -> new GeneratorTransaction(randomAccount(), randomAccount(), randomAmount())).toList();
    }

    private static float randomAmount() {
        int base = Math.abs(random.nextInt());
        int partial = Math.abs(random.nextInt() % 100);
        return base + (partial / 100.0f);
    }

    private static String randomAccount() {
        final int ACCOUNT_NO_SIZE = 26;
        final boolean useLetters = false;
        final boolean useNumbers = true;
        return RandomStringUtils.random(ACCOUNT_NO_SIZE, useLetters, useNumbers);
    }

    static class GeneratorTransaction {
        private String debitAccount; // min & max length = 26

        private String creditAccount; // min & max length = 26

        @JsonSerialize(using = TwoPlacesSerializer.class)
        private float amount;

        GeneratorTransaction(String debitAccount, String creditAccount, float amount) {
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
    }

    static class TwoPlacesSerializer extends JsonSerializer<Float> {
        @Override
        public void serialize(Float value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            var formatter = new DecimalFormat();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(false);
            DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
            sym.setDecimalSeparator('.');
            formatter.setDecimalFormatSymbols(sym);
            final String output = formatter.format(value);
            gen.writeNumber(output);
        }
    }
}
