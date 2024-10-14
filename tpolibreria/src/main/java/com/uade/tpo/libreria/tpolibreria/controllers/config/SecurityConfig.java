package com.uade.tpo.libreria.tpolibreria.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.Optional;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.Role;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.UsuarioRepository;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private final CarritoRepository carritoRepository;
        private final UsuarioRepository usuarioRepository;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers("/error/**").permitAll()
                                        .requestMatchers("/carritos/**").permitAll()
                                        .requestMatchers("/usuarios/**").permitAll()
                                        .requestMatchers("/productosCarrito/**").permitAll()
                                        .requestMatchers("/giftcards/**").permitAll()
                                        .requestMatchers("/generos/**").permitAll()
                                        .requestMatchers("/libros/**").permitAll()
                                        .requestMatchers("/ordenes/**").permitAll()
                                        .requestMatchers("/images/**").permitAll()

                                        /* 
                                        //CARRITO
                                        .requestMatchers("/carritos/{carritoId}").access((authentication, context) -> {
                                                String carritoId = context.getVariables().get("carritoId");
                                                Optional<Carrito> carrito = carritoRepository.findById(carritoId);
                                                boolean esPropietario = (carrito.isPresent() && carrito.get().getMail().equals(authentication.get().getName()));
                                                boolean esAdmin = authentication.get().getAuthorities().stream()
                                                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                                return new AuthorizationDecision(esPropietario || esAdmin); })
                                        .requestMatchers("/carritos/**").hasAnyAuthority(Role.ADMIN.name())
                                        
                                        //USUARIOS
                                         .requestMatchers(HttpMethod.DELETE,"/usuarios/{usuarioId}").access((authentication, context) -> {
                                                Long usuarioId = Long.parseLong(context.getVariables().get("usuarioId"));
                                                Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
                                                boolean esPropietario = (usuario.isPresent() && usuario.get().getMail().equals(authentication.get().getName()));
                                                boolean esAdmin = authentication.get().getAuthorities().stream()
                                                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                                return new AuthorizationDecision(esPropietario || esAdmin); })
                                        .requestMatchers(HttpMethod.PUT,"/usuarios/{usuarioId}").access((authentication, context) -> {
                                                Long usuarioId = Long.parseLong(context.getVariables().get("usuarioId"));
                                                Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
                                                boolean esPropietario = (usuario.isPresent() && usuario.get().getMail().equals(authentication.get().getName()));
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
                                        .requestMatchers("/usuarios/**").hasAnyAuthority(Role.ADMIN.name())

                                        //PRODUCTOS CARRITO
                                        .requestMatchers(HttpMethod.POST, "/productosCarrito").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())
                                        .requestMatchers("/productosCarrito/{isbn}/ActualizarCantLibro").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())
                                        .requestMatchers("/productosCarrito/{mail}/listaDeProductosCarritoByMail").access((authentication, context) -> {
                                                String mail = context.getVariables().get("mail");
                                                Carrito carrito = carritoRepository.findByMail(mail);
                                                boolean esPropietario = (carrito.getMail().equals(authentication.get().getName()));
                                                boolean esAdmin = authentication.get().getAuthorities().stream()
                                                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
                                                return new AuthorizationDecision(esPropietario || esAdmin); })
                                        .requestMatchers("/productosCarrito/EliminarprodCarrito").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())
                                        .requestMatchers("/productosCarrito/**").hasAnyAuthority(Role.ADMIN.name())

                                        //GIFTCARDS
                                        .requestMatchers("/giftcards/**").hasAnyAuthority(Role.ADMIN.name())

                                        //GENEROS
                                        .requestMatchers(HttpMethod.GET,"/generos/**").permitAll()
                                        .requestMatchers("/generos/**").hasAnyAuthority(Role.ADMIN.name())

                                        //LIBROS
                                        .requestMatchers(HttpMethod.GET,"/libros/**").permitAll()
                                        .requestMatchers("/libros/**").hasAnyAuthority(Role.ADMIN.name())

                                        //ORDENES
                                        .requestMatchers(HttpMethod.POST, "/ordenes/**").hasAnyAuthority(Role.USUARIO.name(), Role.ADMIN.name())//Agregar que solo pueda hacerlo si es su usuario
                                        .requestMatchers("/ordenes/**").hasAnyAuthority(Role.ADMIN.name())
                                        
                                        //IMAGENES
                                        .requestMatchers("/images/**").hasAnyAuthority(Role.ADMIN.name())*/

                                        .anyRequest()
                                        .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
