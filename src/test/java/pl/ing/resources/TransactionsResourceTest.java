package pl.ing.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.ing.business.dto.transactions.Account;
import pl.ing.business.dto.transactions.Transaction;

import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TransactionsResourceTest {
    @Test
    void emptyList() {
        given()
                .body(Collections.emptyList())
                .header("Content-Type", "application/json")
                .when().post("/transactions/report")
                .then().statusCode(200)
                .body(is("[]"));
    }

    @Test
    void nullCase() {
        given()
                .body("null")
                .header("Content-Type", "application/json")
                .when().post("/transactions/report")
                .then().statusCode(200)
                .body(is("[]"));
    }

    @Nested
    class RequestDeserializationTest {
        @Test
        void validData() {
            String body = """
                    [
                      {
                          "debitAccount": "32309111922661937852684864",
                          "creditAccount": "06105023389842834748547303",
                          "amount": 10.90
                      },
                      {
                          "debitAccount": "31074318698137062235845814",
                          "creditAccount": "66105036543749403346524547",
                          "amount": 200.90
                      }
                    ]
                    """;
            List<Transaction> transactions = readValue(body);

            assertThat(transactions).containsExactly(
                    new Transaction("32309111922661937852684864", "06105023389842834748547303", 10.90f),
                    new Transaction("31074318698137062235845814", "66105036543749403346524547", 200.90f)
            );
        }

        List<Transaction> readValue(String value) {
            try {
                return new ObjectMapper().readValue(value, new TypeReference<List<Transaction>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    class ResponseSerializationTest {
        @Test
        void validData() throws JsonProcessingException {
            var response = List.of(
                    new Account("32309111922661937852684864", 1, 1, 5.01f),
                    new Account("12309111922661937852684864", 2, 1, 5.00f)
            );
            String json = new ObjectMapper().writeValueAsString(response);
            assertThat(json).isEqualTo("[{\"account\":\"32309111922661937852684864\",\"debitCount\":1,\"creditCount\":1,\"balance\":5.01},{\"account\":\"12309111922661937852684864\",\"debitCount\":2,\"creditCount\":1,\"balance\":5.0}]");
        }
    }
}
