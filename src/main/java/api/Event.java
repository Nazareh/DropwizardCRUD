package api;


public class Event {
    private long id;
    private String name;
    private String description;
    private String location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void updateExceptId(Event event) {
        this.name = event.name;
        this.description = event.description;
        this.location = event.getLocation();
    }

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof Event)) return false;
            Event event = (Event) obj;

        return event.getId() == this.id &&
                event.getLocation().equals(this.location) &&
                event.getDescription().equals(this.description)&&
                event.getName().equals(this.name);

    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
