package com.desarrollo.configurations;

import com.desarrollo.entities.administracion.Usuario;
import com.desarrollo.repositories.administracion.UsuarioRepository;
import com.desarrollo.types.administracion.UsuarioType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.InternalException;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author ivanLuna
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final CipherData cipherData;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public static final String HEADER_AUTH = "Authorization";

    @Bean
    public AuthenticationManager authenticationManager() {
        return new AuthenticationManager() {
            @Override
            @Transactional
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String nombreUsuario = authentication.getName();
                String password = authentication.getCredentials().toString();
                Usuario usuario = usuarioRepository.buscarPorNombreUsuario(nombreUsuario);
                if ( passwordEncoder.matches( password, usuario.getPassword() ) ) {
                    return new UsernamePasswordAuthenticationToken( nombreUsuario, password );
                }
                throw new AuthenticationException("Invalid credentials") {};
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                csrf( csrf -> csrf.disable() )
                .cors( cors -> cors.disable() )
                .authorizeHttpRequests( auth ->
                        auth.requestMatchers("/login")
                                .permitAll().anyRequest().authenticated() )
                .sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS) )
                .addFilter( new JWTAuthenticationFilter( authenticationManager() ) )
                .addFilter( new JWTAuthorizationFilter( authenticationManager() ) )
                .logout( logout -> logout.logoutUrl( "/logout" )
                        .permitAll()
                        .logoutSuccessHandler( new CustomLogoutSuccessHandler() )
                        .invalidateHttpSession( true ).clearAuthentication( true )
                        .addLogoutHandler( new SecurityContextLogoutHandler() )
                        .logoutRequestMatcher( new AntPathRequestMatcher( "/logout", "POST" ) )
                )
                .build();
    }

    public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private AuthenticationManager authenticationManager;

        public JWTAuthenticationFilter ( AuthenticationManager authenticationManager) {
            this.authenticationManager = authenticationManager;
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            try {
                UsuarioType usuarioType = objectMapper.readValue(request.getInputStream(), UsuarioType.class);
                request.setAttribute("nombreUsuario", usuarioType.getNombreUsuario());
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuarioType.getNombreUsuario(), usuarioType.getPassword()));
            } catch ( AuthenticationException e) {
                throw new AuthenticationException("Credenciales incorrectas.") {};
            } catch ( IOException e) {
                throw new RuntimeException( "Error al parsear los accesos del usuario." );
            }
        }

        @Override
        @Transactional
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//            Usuario usuario = (Usuario) authResult.getPrincipal();
            Usuario usuario = usuarioRepository.buscarPorNombreUsuario(authResult.getPrincipal().toString());
            UsuarioType usuarioType = serializarUsuario(usuario);
            JwtBuilder jwtBuilder = Jwts.builder();
            LocalDateTime fechaExpiracion = LocalDateTime.now().plusMinutes(30);
            usuarioType.setFechaExpiracion( Date.from( fechaExpiracion.atZone( ZoneId.systemDefault() ).toInstant() ) );
            Map claims = new HashMap();
            claims.put("usaurio", usuarioType);
            jwtBuilder.setSubject(usuarioType.getNombreUsuario())
                    .addClaims( claims )
                    .setExpiration( usuarioType.getFechaExpiracion() )
                    .signWith( cipherData.getSignatureAlgorithm(), cipherData.getPrivateKey() );
            response.setHeader( HEADER_AUTH, jwtBuilder.compact() );
        }

        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
            String nombreUs = String.valueOf(request.getAttribute("nombreUsuario"));
            request.removeAttribute("nombreUsuario");
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            response.setContentType( failed.getMessage() );
            response.sendError( HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage() );
            throw new AuthenticationException( failed.getMessage() ) {};
        }
    }

    private UsuarioType serializarUsuario (Usuario usuario) {
        UsuarioType usuarioType = new UsuarioType();
        BeanUtils.copyProperties(usuario, usuarioType, "password");
        return usuarioType;
    }

    public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

        public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
            super(authenticationManager);
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            String jwt = request.getHeader(HEADER_AUTH);
            try {
                if ( Objects.isNull( jwt ) || ( jwt.equalsIgnoreCase("[object Object]") || jwt.equalsIgnoreCase("null") ) ) {
                    chain.doFilter(request, response);
                    return;
                }
                UsernamePasswordAuthenticationToken authentication = getAuthentication(jwt.replace("Bearer ", ""));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }  catch (IOException | ServletException | ExpiredJwtException | MalformedJwtException |
                      SignatureException | UnsupportedJwtException | IllegalArgumentException exception) {
                throw new InternalException("Error al validar el jwt");
            }
        }

        private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
             if (jwt != null) {
                 // Importante: pasar la clave pública para poder validar la firma al parsear
                 Jws<Claims> parseClaimsJws = Jwts.parser()
                         .setSigningKey( cipherData.getPublicKey() )
                         .build().parseClaimsJws(jwt);
                 //ORIGINAL
                 //Jws<Claims> parseClaimsJws = Jwts.parser()
                 // .build().parseClaimsJws(jwt);
                 if( SignatureAlgorithm.forName(parseClaimsJws.getHeader().getAlgorithm()) != cipherData.getSignatureAlgorithm() ){
                    throw new RuntimeException("Token Corrupto");
                }
                Claims body = parseClaimsJws.getBody();
                String user = body.getSubject();
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            }
            return null;
        }

    }

    public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String jwt = request.getHeader( HEADER_AUTH );
            if (jwt != null || ( jwt.equalsIgnoreCase("[object Object]") || jwt.equalsIgnoreCase("null") ) ) {
                Claims claims = getClaims(jwt);
                if (Objects.isNull(claims)) {
                    throw new IOException("Error al parsear el JWT. No se logro leer el Hash de Acceso");
                }
                String username = claims.getSubject();
                Usuario usuario = usuarioRepository.buscarPorNombreUsuario( username );
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            response.setHeader(HEADER_AUTH, null);
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }

    private Claims getClaims(String jwt) {
        if (Objects.nonNull(jwt) && !jwt.isEmpty() && !jwt.isBlank()) {
            Jws<Claims> parseClaimsJws = Jwts.parser()
                    .setSigningKey( cipherData.getPublicKey() )
                    .build().parseClaimsJws(jwt);
            if( SignatureAlgorithm.forName(parseClaimsJws.getHeader().getAlgorithm()) != cipherData.getSignatureAlgorithm() ){
                throw new RuntimeException("Token Corrupto");
            }
            return parseClaimsJws.getBody();
        }
        return null;
    }

}
