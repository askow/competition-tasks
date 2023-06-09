package pl.ing.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.ing.business.dto.atm.ATM;
import pl.ing.business.dto.atm.RequestType;
import pl.ing.business.dto.atm.Task;

import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AtmsResourceTest {
    @Test
    void emptyList() {
        given()
                .body(Collections.emptyList())
                .header("Content-Type", "application/json")
                .when().post("/atms/calculateOrder")
                .then().statusCode(200)
                .body(is("[]"));
    }

    @Test
    void nullCase() {
        given()
                .body("null")
                .header("Content-Type", "application/json")
                .when().post("/atms/calculateOrder")
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
                        "region": 4,
                        "requestType": "STANDARD",
                        "atmId": 1
                      },
                      {
                        "region": 1,
                        "requestType": "STANDARD",
                        "atmId": 1
                      }
                    ]
                    """;
            List<Task> tasks = readValue(body);

            assertThat(tasks).containsExactly(
                    new Task(4, RequestType.STANDARD, 1),
                    new Task(1, RequestType.STANDARD, 1)
            );
        }

        List<Task> readValue(String value) {
            try {
                return new ObjectMapper().readValue(value, new TypeReference<List<Task>>() {});
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
                    new ATM(1, 1),
                    new ATM(1, 2)
            );
            String json = new ObjectMapper().writeValueAsString(response);
            assertThat(json).isEqualTo("[{\"region\":1,\"atmId\":1},{\"region\":1,\"atmId\":2}]");
        }
    }
}
