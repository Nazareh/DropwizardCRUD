package core;

import api.Event;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;


public class DummyEventRepositoryTest {

    private EventRepository repository;

    @Before
    public void setUp() {
        repository = new DummyEventRepository();
    }

    @Test
    public void testFindById(){
        Event newEvent = new Event();

        newEvent.setDescription("New object description");
        newEvent.setLocation("some location");
        newEvent.setName("any name");

        Event createdEvent =  repository.create(newEvent);

        assertSame(createdEvent,
                    repository
                            .findById(createdEvent.getId())
                            .orElseThrow(() -> new RuntimeException("Object no found.")));
    }

    @Test
    public void testDelete(){
        long idToBeDeleted = repository.findAll().stream().findAny().get().getId();
        repository.delete(idToBeDeleted);
        repository.findById(idToBeDeleted).ifPresent(a -> fail() );
    }

    @Test
    public void testUpdate(){
        Event eventToBeUpdated = repository
                .findAll()
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("Object no found."));
        long eventId = eventToBeUpdated.getId();
        eventToBeUpdated.setLocation("some location");
        eventToBeUpdated.setName("any name");
        eventToBeUpdated.setDescription("random description");

        repository.update(eventId,eventToBeUpdated);

        assertSame(eventToBeUpdated,repository.findById(eventId).get());
    }

    @Test
    public void testCreate(){

        long nextId = repository.getNextId();
        Event newEvent = new Event();
        newEvent.setId(nextId);
        newEvent.setDescription("New object description");
        newEvent.setLocation("some location");
        newEvent.setName("any name");

        Event createdEvent =  repository.create(newEvent);

        assertSame(newEvent,createdEvent);

    }

}