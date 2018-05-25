package core;

import api.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    List<Event> findAll();

    Optional<Event> findById(long id);

    Event create(Event event);

    Optional<Event> update(Long id, Event event);

    void delete(Long id);

    long getNextId();

}
