package pl.ing.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AtmsResourceTest {
    @Test
    public void emptyList() {
        given()
                .body(Collections.emptyList())
                .header("Content-Type", "application/json")
                .when().post("/atms/calculateOrder")
                .then().statusCode(200)
                .body(is("[]"));
    }

    @Test
    public void nullCase() {
        given()
                .body("null")
                .header("Content-Type", "application/json")
                .when().post("/atms/calculateOrder")
                .then().statusCode(200)
                .body(is("[]"));
    }
}
