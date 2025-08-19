package system.flight.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        // Aircrafts
                        .requestMatchers(HttpMethod.GET, "/api/aircrafts/**").hasAnyRole("USER", "FLIGHT_MANAGER", "FLIGHT_OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/aircrafts").hasAnyRole("FLIGHT_MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/aircrafts/**").hasAnyRole("ADMIN", "FLIGHT_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/aircrafts/**").hasAnyRole("ADMIN", "FLIGHT_OWNER", "FLIGHT_MANAGER")

                        // Airlines
                        .requestMatchers(HttpMethod.GET, "/api/airlines/**").hasAnyRole("FLIGHT_MANAGER", "FLIGHT_OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/airlines").hasAnyRole("FLIGHT_OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/airlines/**").hasAnyRole("FLIGHT_OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/airlines/**").hasAnyRole("FLIGHT_OWNER", "ADMIN")

                        // Bookings
                        .requestMatchers("/api/bookings/**").hasAnyRole("USER", "ADMIN")

                        // Routes
                        .requestMatchers(HttpMethod.GET, "/api/routes/**").hasAnyRole("USER", "FLIGHT_MANAGER", "FLIGHT_OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/routes").hasAnyRole("ADMIN", "FLIGHT_MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/routes/**").hasAnyRole("ADMIN", "FLIGHT_MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/routes/**").hasAnyRole("ADMIN", "FLIGHT_MANAGER")

                        // Roles
                        .requestMatchers("/api/roles/**").hasRole("ADMIN")

                        // Users
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN","FLIGHT_MANAGER", "FLIGHT_OWNER", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN","FLIGHT_MANAGER", "FLIGHT_OWNER", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("ADMIN","FLIGHT_MANAGER", "FLIGHT_OWNER", "USER")

                        // Catch-all
                        .anyRequest().authenticated()
                )
                .authenticationProvider(customAuthenticationProvider)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }
}
