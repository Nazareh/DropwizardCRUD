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
import static org.assertj.core.api.Assertions.*;
import static io.restassured.RestAssured.given;


public class EventResourceTest {
    private static RequestSpecification requestSpecification;

    private Event createDummyEvent() {
        Event event = new Event();
        event.setName("Birthday");
        event.setLocation("my house");
        event.setDescription("BYO drinks");
        return event;
    }

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
            assertThat(events.size()).isGreaterThan(0);
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
        assertThat(event.getId()).isEqualTo(1l);
    }
    @Test
    public void testPut (){
        Event newEvent = createDummyEvent();
        Event updatedEvent = given()
                .spec(requestSpecification)
                .body(newEvent)
                .when()
                .put("api/events/1")
                .then()
                .statusCode(200)
                .extract().as(Event.class);
        assertThat(updatedEvent).isEqualToIgnoringGivenFields(newEvent,"id");
    }



    @Test
    public void testPost (){
        Event newEvent = createDummyEvent();

        Event createdEvent = given()
                .spec(requestSpecification)
                .body(newEvent)
                .when()
                .post("api/events")
                .then()
                .statusCode(200)
                .extract().as(Event.class);

        assertThat(createdEvent).isEqualToIgnoringGivenFields(newEvent,"id");


    }
    @Test
    public void testDelete (){

        //create a new event
        Event createdEvent = given()
                .spec(requestSpecification)
                .body(createDummyEvent())
                .when()
                .post("api/events")
                .then()
                .statusCode(200)
                .extract().as(Event.class);

        //save the event ID to later verify if it was deleted
        long createdEventId = createdEvent.getId();

        //delete event
         given()
                .spec(requestSpecification)
                .when()
                .delete("api/events/"+ createdEventId)
                .then()
                .statusCode(200);

         //try to get the event but receive a 404 error instead (not found)
         given()
                .spec(requestSpecification)
                .when()
                .get("api/events/"+createdEventId)
                .then()
                .statusCode(404);


    }
}
