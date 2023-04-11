package pl.ing.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.ing.business.dto.onlinegame.Clan;
import pl.ing.business.dto.onlinegame.Order;
import pl.ing.business.dto.onlinegame.Players;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class OnlineGameResourceTest {
    @Test
    void nullCase() {
        given()
                .body("null")
                .header("Content-Type", "application/json")
                .when().post("/onlinegame/calculate")
                .then().statusCode(200)
                .body(is("[]"));
    }

    @Test
    void nullClans() {
        given()
                .body("{\"groupCount\": 5, \"clans\": null}")
                .header("Content-Type", "application/json")
                .when().post("/onlinegame/calculate")
                .then().statusCode(200)
                .body(is("[]"));
    }

    @Nested
    class RequestDeserializationTest {
        @Test
        void validData() {
            String body = """
                    {
                        "groupCount": 6,
                        "clans": [
                            {
                                "numberOfPlayers": 10,
                                "points": 50
                            },
                            {
                                "numberOfPlayers": 10,
                                "points": 50
                            }
                        ]
                    }
                    """;
            Players players = readValue(body);

            assertThat(players.getGroupCount()).isEqualTo(6);
            assertThat(players.getClans()).containsExactly(
                    new Clan(10, 50),
                    new Clan(10, 50)
            );
        }

         Players readValue(String value) {
            try {
                return new ObjectMapper().readValue(value, Players.class);
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
                    List.of(new Order(1, 10), new Order(2, 20)),
                    List.of(new Order(3, 30))
            );
            String json = new ObjectMapper().writeValueAsString(response);
            assertThat(json)
                    .isEqualTo("[[{\"numberOfPlayers\":1,\"points\":10},{\"numberOfPlayers\":2,\"points\":20}],[{\"numberOfPlayers\":3,\"points\":30}]]");
        }
    }
}
