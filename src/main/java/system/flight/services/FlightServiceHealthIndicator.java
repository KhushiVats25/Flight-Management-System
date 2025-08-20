package system.flight.services;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@Component
public class FlightServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String flightServiceHealthUrl = "http://localhost:8086/api/aircrafts"; // Replace with actual endpoint

    @Override
    public Health health() {
        boolean serviceUp = checkFlightService();
        return serviceUp
                ? Health.up().withDetail("FlightService", "Available").build()
                : Health.down().withDetail("FlightService", "Unreachable or unhealthy").build();
    }

    private boolean checkFlightService() {
        try {
            // You can use a dedicated health endpoint if available
            restTemplate.getForEntity(flightServiceHealthUrl, String.class);
            return true;
        } catch (HttpClientErrorException | ResourceAccessException ex) {
            return false;
        }
    }
}
