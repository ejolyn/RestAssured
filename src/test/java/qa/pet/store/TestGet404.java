package qa.pet.store;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pet.store.ResponseMessage;

public class TestGet404 {

    private static int idPet = 1000;
    private static ResponseMessage error;

    @BeforeAll
    public static void deleteExistingPet() {
        ResponseMessage errorCopy = new ResponseMessage();
        errorCopy.setCode(1);
        errorCopy.setMessage("Pet not found");
        errorCopy.setType("error");
        TestGet404.error = errorCopy;
        // удаление предыдущего пета, если таковой имелся
        RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .delete("https://petstore.swagger.io/v2/pet/" + idPet);
    }

    @Test
    public void testGet404() {
        ResponseMessage restError = RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .get("https://petstore.swagger.io/v2/pet/" + idPet)
                .then().statusCode(404)
                .extract().as(ResponseMessage.class);
        Assertions.assertEquals(error, restError);
    }
}
