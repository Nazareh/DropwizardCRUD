package healthchecks;

import com.codahale.metrics.health.HealthCheck;

public class EventsApplicationHealthCheck extends HealthCheck {


    public EventsApplicationHealthCheck() {
        super();
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();


    }
}
