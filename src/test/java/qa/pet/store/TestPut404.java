package qa.pet.store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pet.store.Category;
import pet.store.ResponseMessage;
import pet.store.Pet;
import pet.store.TagsItem;

import java.util.Arrays;

@TestMethodOrder(OrderAnnotation.class)
public class TestPut404 {

    private static Pet testPet;
    private static int idPet = 5500;
    private static ResponseMessage error;

    @BeforeAll
    private static void deletePet() {
        Pet testPetCopy = new Pet();
        testPetCopy.setId(idPet);
        testPetCopy.setName("Everlasting");
        Category testCategory = new Category();
        testCategory.setId(2000);
        testCategory.setName("Winter");
        testPetCopy.setCategory(testCategory);
        TagsItem testTag1 = new TagsItem();
        testTag1.setId(3000);
        testTag1.setName("Seasons");
        TagsItem testTag2 = new TagsItem();
        testTag2.setId(3001);
        testTag2.setName("Something else");
        testPetCopy.setTags(Arrays.asList(testTag1, testTag2));
        TestPut404.testPet = testPetCopy;

        ResponseMessage errorCopy = new ResponseMessage();
        errorCopy.setCode(1);
        errorCopy.setMessage("Pet not found");
        errorCopy.setType("error");
        TestPut404.error = errorCopy;
        // удаление предыдущего пета, если таковой имелся
        RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .delete("https://petstore.swagger.io/v2/pet/" + idPet);
    }


    @Test
    @Order(1)
    public void testPut404Put() {
        ResponseMessage errorMessage = RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .body(testPet)
                .put("https://petstore.swagger.io/v2/pet")
                .then().statusCode(404)
                .extract().as(ResponseMessage.class);
        Assertions.assertEquals(error, errorMessage);
    }

    @Test
    @Order(2)
    public void testPut404Get() {
        Pet restAssuredPet = RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .get("https://petstore.swagger.io/v2/pet/" + idPet)
                .then().statusCode(404)
                .extract().as(Pet.class);
        Assertions.assertEquals(testPet, restAssuredPet);
    }
}
