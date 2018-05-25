package core;

import api.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class DummyEventRepository implements EventRepository {
    private static final String DATA_SOURCE = "dummy_data.json";
    private List<Event> events;

    public DummyEventRepository() {
        try {
            initData();
        } catch (IOException e) {
            throw new RuntimeException(DATA_SOURCE + " missing or unreadable.", e);
        }
    }
        private void initData() throws IOException {
            URL url = Resources.getResource(DATA_SOURCE);
            String json = Resources.toString(url,Charsets.UTF_8);
            events = new ObjectMapper().readValue(json, new TypeReference<List<Event>>(){});
        }

    @Override
    public List<Event> findAll() {
        return events;
    }

    @Override
    public Optional<Event> findById(long id) {
        return  events.stream().filter(e -> e.getId() == id ).findFirst();
    }

    public synchronized long getNextId(){
        Optional<Long> maxId = events.stream()
                .map(Event::getId)
                .max(Long::compare);
        return maxId.map(x -> x+1).orElse(1L);
    }
    @Override
    public Event create(Event event) {
        event.setId(getNextId());
        events.add(event);

        return event;
    }

    @Override
    public Optional<Event> update(Long id, Event event) {

        Optional<Event> existingEvent = findById(id);
        existingEvent.ifPresent(e -> e.updateExceptId(event));
        return existingEvent;

    }

    @Override
    public void delete(Long id) {
        events.removeIf(a -> a.getId() == id);
    }
}
