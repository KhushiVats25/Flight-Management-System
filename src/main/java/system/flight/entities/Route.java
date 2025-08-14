package system.flight.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

    @Entity
    @Table(name = "routes")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Route {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "route_id")
        private int routeId;

        @Column(name="source_city", nullable = false, length=50)
        private String sourceCity;

        @Column(name="destination_city", nullable = false, length=50)
        private String destinationCity;

        @Column(name="distance_in_km", nullable = false)
        private int distanceKm;

        @Column(name="estimated_time" , nullable=false)
        private LocalTime estimatedTime;

        @Column(name="arrival_time" , nullable=false)
        private LocalDateTime arrivalTime;

        @Column(name="departure_time" , nullable=false)
        private LocalDateTime departureTime;
    }


