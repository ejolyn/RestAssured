package qa.pet.store;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestDelete404 {

    private static int idPet = 6500;

    @BeforeAll
    private static void deletePet() {
        // удаление предыдущего пета, если таковой имелся
        RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .delete("https://petstore.swagger.io/v2/pet/" + idPet);
    }

    @Test
    public void testDelete404Delete() {
        RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .delete("https://petstore.swagger.io/v2/pet/" + idPet)
                .then().statusCode(404)
                .body(Matchers.emptyOrNullString());
    }
}
