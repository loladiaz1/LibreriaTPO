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
import java.util.Optional;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.entity.Role;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.ProductoCarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.UsuarioRepository;
import com.uade.tpo.libreria.tpolibreria.service.CarritoService;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private final CarritoRepository carritoRepository;
        private final ProductoCarritoRepository productoCarritoRepository;
        private final UsuarioRepository usuarioRepository;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers("/error/**").permitAll()
                                        .requestMatchers("/carritos/{carritoId}").access((authentication, context) -> {
                                                String carritoId = context.getVariables().get("carritoId");
                                                Optional<Carrito> carrito = carritoRepository.findById(carritoId);
                                                boolean esPropietario = (carrito.isPresent() && carrito.get().getMail().equals(authentication.get().getName()));
                                                boolean esAdmin = authentication.get().getAuthorities().stream()
                                                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                                return new AuthorizationDecision(esPropietario || esAdmin); })
                                         .requestMatchers("/usuarios/mail/{UsuarioMail}").access((authentication, context) -> {
                                                String usuarioMail = context.getVariables().get("UsuarioMail");
                                                Optional<Usuario> usuario = usuarioRepository.findByMail(usuarioMail);
                                                boolean esPropietario = (usuario.isPresent() && usuario.get().getMail().equals(authentication.get().getName()));
                                                boolean esAdmin = authentication.get().getAuthorities().stream()
                                                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                                return new AuthorizationDecision(esPropietario || esAdmin); })
                                        .requestMatchers("/carritos/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/productosCarrito/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/usuarios/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/generos/**").permitAll()
                                        .requestMatchers("/libros/**").permitAll()
                                        .requestMatchers("/giftcards/**").permitAll()
                                        .anyRequest()
                                        .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
