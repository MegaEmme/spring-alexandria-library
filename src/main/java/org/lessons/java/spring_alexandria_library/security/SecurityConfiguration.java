package org.lessons.java.spring_alexandria_library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                // I percorsi per creare e modificare i libri e le categorie sono per soli
                // ADMIN.
                .requestMatchers("books/create", "books/edit/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/books/**").hasAuthority("ADMIN")
                .requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
                // I percorsi per i libri sono accessibili sia da USER che da ADMIN.
                .requestMatchers("/books", "/books/**").hasAnyAuthority("USER", "ADMIN")
                // Tutti gli altri percorsi sono accessibili a chiunque.
                .requestMatchers("/**").permitAll());

        http.formLogin(Customizer.withDefaults());
        http.logout(Customizer.withDefaults());
        http.exceptionHandling(Customizer.withDefaults());

        // Disabilito CORS e CSRF, che non si trovano nel blocco authorizeHttpRequests
        http.cors(cors -> cors.disable());
        http.csrf(csrf -> csrf.disable());

        return http.build();

        // VERSIONE PRECEDENTE, VISTA A LEZIONE...
        //
        // .requestMatchers("books/create", "books/edit/**").hasAuthority("ADMIN")
        // .requestMatchers(HttpMethod.POST, "/books/**").hasAuthority("ADMIN")
        // .requestMatchers("/categories", "/categories/**").hasAuthority("ADMIN")
        // .requestMatchers("/books", "/books/**").hasAnyAuthority("USER", "ADMIN")
        // .requestMatchers("/**").permitAll()
        // .and().formLogin()
        // .and().logout()
        // .and().exceptionHandling();
    }

    @Bean
    @SuppressWarnings("deprecation")
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // QUESTO PROVIDER USERA X COME SERVIZIO DI RECUPERO DEGLI UTENTI VIA USERNAME
        // GLI PASSIAMO USERDETAILSERVICE, CHE DOBBIAMO ANCORA CREARE --> CREAZIONE
        // USERDETAILSERVICE
        authProvider.setUserDetailsService(null);
        // QUESTO PROVIDER USERA Y COME PASSWORD ENCODER
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
