package id.thedev.tumit.sbdemoapi.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //        http.authorizeHttpRequests(r -> r.requestMatchers("/cashcards/**").authenticated())
        //                .httpBasic(Customizer.withDefaults())
        //                .csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(r -> r.requestMatchers("/cashcards/**").hasRole("CARD-OWNER"))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {

        var userBuilder = User.builder();

        var sarah = userBuilder
                .username("sarah1")
                .password(passwordEncoder.encode("123"))
                .roles("CARD-OWNER")
                .build();

        var hankOwnsNoCards = userBuilder
                .username("hank-owns-no-card")
                .password(passwordEncoder.encode("456"))
                .roles("NON-OWNER")
                .build();

        return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards);
    }
}
