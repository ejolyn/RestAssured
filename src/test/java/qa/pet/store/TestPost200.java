package qa.pet.store;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import pet.store.Category;
import pet.store.Pet;
import pet.store.TagsItem;

import java.util.Arrays;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPost200 {

    private static Pet testPet;
    private static int idPet = 1000;

    @BeforeAll
    private static void createPet() {
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
        TestPost200.testPet = testPetCopy;
    }

    @Test
    @Order(1)
    public void testPostPost() {
        Pet restAssuredPet = RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .body(testPet)
                .post("https://petstore.swagger.io/v2/pet")
                .then().statusCode(200)
                .extract().as(Pet.class);
        Assertions.assertEquals(testPet, restAssuredPet);
    }

    @Test
    @Order(2)
    public void testPostGet() {
        Pet restAssuredPet = RestAssured.given().contentType(ContentType.JSON)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .get("https://petstore.swagger.io/v2/pet/1000")
                .then().statusCode(200)
                .extract().as(Pet.class);
        Assertions.assertEquals(testPet, restAssuredPet);
    }
}
