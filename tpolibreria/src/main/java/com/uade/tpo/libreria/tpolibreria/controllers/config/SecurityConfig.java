package com.uade.tpo.libreria.tpolibreria.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.Role;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.service.CarritoService;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private CarritoRepository carritoRepository;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                                .requestMatchers("/error/**").permitAll()
                                                .requestMatchers("/generos/**").permitAll()
                                                .requestMatchers("/libros/**").permitAll()
                                                .requestMatchers("/carritos/**").hasAnyAuthority(Role.USUARIO.name())
                                                .requestMatchers("/productosCarrito/**").hasAnyAuthority(Role.USUARIO.name())
                                                .requestMatchers("/usuarios/**").hasAnyAuthority(Role.ADMIN.name())
                                                //.requestMatchers("/carritos/{carritoId}").access((authentication, context) -> {
                                                //        List<Carrito> carrito = carritoRepository.findByMail(authentication.get().getName());
                                                //        return new AuthorizationDecision(carrito.isEmpty()); })
                                                //.requestMatchers("/carritos/{carritoId}").hasAnyAuthority(Role.USUARIO.name())
                                                //.requestMatchers("/productosCarrito/{productoCarritoId}").hasAnyAuthority(Role.USUARIO.name())
                                                .requestMatchers("/usuarios/{UsuarioId}").hasAnyAuthority(Role.USUARIO.name())
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
