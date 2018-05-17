package core;

import api.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    public abstract List<Event> findAll();

    public abstract Optional<Event> findById(long id);

    public abstract Event save (Event event);

    public abstract Optional<Event> update(Long id, Event event);

    public abstract void delete(Long id);

}
