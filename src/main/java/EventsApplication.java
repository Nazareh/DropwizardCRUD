import core.DummyEventRepository;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import resources.EventResource;

import java.text.SimpleDateFormat;

public class EventsApplication extends Application<EventsConfiguration> {
    public static void main(String[] args) throws Exception {

        new EventsApplication().run(args);

    }
    @Override
    public void run(EventsConfiguration eventsConfiguration, Environment environment) throws Exception {
        environment.getObjectMapper().setDateFormat(new SimpleDateFormat(eventsConfiguration.getDateFormat()));

        environment.jersey().register(new EventResource(new DummyEventRepository()));


    }
}
