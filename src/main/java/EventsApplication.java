import core.DummyEventRepository;
import healthchecks.EventsApplicationHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import resources.EventResource;

public class EventsApplication extends Application<EventsConfiguration> {
    public static void main(String[] args) throws Exception {

        new EventsApplication().run(args);

    }
    @Override
    public void run(EventsConfiguration eventsConfiguration, Environment environment) {
        environment.jersey().register(new EventResource(new DummyEventRepository()));
        environment.healthChecks().register("sample-app2",new EventsApplicationHealthCheck());
        environment.healthChecks().register("sample-app",new EventsApplicationHealthCheck());

    }
}
