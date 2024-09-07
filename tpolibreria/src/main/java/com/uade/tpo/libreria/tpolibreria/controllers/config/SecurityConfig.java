package com.uade.tpo.libreria.tpolibreria.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
=======
>>>>>>> main
import com.uade.tpo.libreria.tpolibreria.entity.Role;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
<<<<<<< HEAD
import com.uade.tpo.libreria.tpolibreria.repository.ProductoCarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.UsuarioRepository;
import com.uade.tpo.libreria.tpolibreria.service.CarritoService;
=======
>>>>>>> main

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
                                       /*  .requestMatchers("/productosCarrito/{productoCarritoId}").access((authentication, context) -> {
                                                String productoCarritoId = context.getVariables().get("productoCarritoId");
                                                Optional<ProductoCarrito> productoCarrito = productoCarritoRepository.findByMail(productoCarritoId);
                                                boolean esPropietario = (carrito.isPresent() && carrito.get().getMail().equals(authentication.get().getName()));
                                                boolean esAdmin = authentication.get().getAuthorities().stream()
                                                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                                return new AuthorizationDecision(esPropietario || esAdmin); })
                                                Hacer prodcuto carrito by mail*/
                                        /* .requestMatchers("/usuarios/{UsuarioId}").access((authentication, context) -> {
                                                Long usuarioId = Long.parseLong(context.getVariables().get("UsuarioId"));
                                                Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
                                                boolean esPropietario = (usuario.isPresent() && usuario.get().getId().equals(authentication.get().getName()));
                                                boolean esAdmin = authentication.get().getAuthorities().stream()
                                                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                                return new AuthorizationDecision(esPropietario || esAdmin); })
                                                hacer usuario by mail*/
                                        .requestMatchers("/carritos/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/productosCarrito/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/usuarios/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/generos/**").permitAll()
                                        .requestMatchers("/libros/**").permitAll()
                                        .anyRequest()
                                        .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}