package core;

import api.Event;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class DummyEventRepository implements EventRepository {
    private static final String DATA_SOURCE = "dummy_data.json";
    private List<Event> events;
    private ObjectMapper mapper;

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
            mapper = new ObjectMapper();
            events = mapper.readValue(json,new TypeReference<List<Event>>(){});

        }

    @Override
    public List<Event> findAll() {
        return events;
    }

    @Override
    public Optional<Event> findById(long id) {
        return events.stream().filter(a -> a.getId() == id).findFirst();
    }
    @Override
    public Event save(Event event) {
        Optional<Long> maxId = events.stream()
                .map(Event::getId)
                .max(Long::compare);
        long nextId = maxId.map(x -> x+1).orElse(1L);
        event.setId(nextId);
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
