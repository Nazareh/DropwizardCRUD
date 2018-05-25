package resources;

import api.Event;
import com.google.common.reflect.TypeToken;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class EventResourceTest {
    private static RequestSpecification requestSpecification;

    @Before
    public void initSpec(){
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:9000")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();

    }
    @Test
    public void testGetAll (){
            List<Event> events= given()
                                        .spec(requestSpecification)
                                        .when()
                                        .get("api/events")
                                        .then()
                                        .statusCode(200)
                                        .extract().as( new TypeToken<List<Event>>(){}.getType());
    }
    @Test
    public void testGetSingleObject (){
        Event event = given()
                .spec(requestSpecification)
                .when()
                .get("api/events/1")
                .then()
                .statusCode(200)
                .extract().as(Event.class);
    }
    @Test
    public void testPut (){
        Event event = new Event();
        event.setName("Birthday");
        event.setLocation("my house");
        event.setDescription("BYO drinks");

       Event updatedEvent = given()
                .spec(requestSpecification)
                .body(event)
                .when()
                .put("api/events/1")
                .then()
                .statusCode(200)
                .extract().as(Event.class);
    }
    @Test
    public void testPost (){
        Event event = new Event();
        event.setName("Birthday");
        event.setLocation("my house");
        event.setDescription("BYO drinks");

        Event createdEvent = given()
                .spec(requestSpecification)
                .body(event)
                .when()
                .post("api/events")
                .then()
                .statusCode(200)
                .extract().as(Event.class);

    }
    @Test
    public void testDelete (){

        Event createdEvent = given()
                .spec(requestSpecification)
                .body(new Event())
                .when()
                .post("api/events")
                .then()
                .statusCode(200)
                .extract().as(Event.class);

        long createdEventId = createdEvent.getId();

         given()
                .spec(requestSpecification)
                .when()
                .delete("api/events/"+ createdEventId)
                .then()
                .statusCode(200);

         given()
                .spec(requestSpecification)
                .when()
                .get("api/events/"+createdEventId)
                .then()
                .statusCode(404);


    }
}
