package com.opp.todo.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.opp.todo.model.TodoRole;
import com.opp.todo.model.TodoUser;
import com.opp.todo.repository.TodoUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final RsaKeyProperties rsaKey;
    private TodoUserRepository userRepository;


    public SecurityConfig(RsaKeyProperties rsaKey, TodoUserRepository userRepository) {
        this.rsaKey = rsaKey;
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(){
       return username -> userRepository.findByUsername(username).get();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return new ProviderManager(provider);
    }


    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKey.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKey.publicKey()).privateKey(rsaKey.privateKey()).build();
        JWKSource<SecurityContext> src = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(src);
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:5174"));
        config.setAllowedMethods(Arrays.asList("POST","GET","DELETE","PUT","PATCH","OPTIONS","HEAD"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        return httpSecurity.csrf(crsf->crsf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request->request.requestMatchers("/api/user","/api/user/**","/api/image","/api/projects/**","/api/activity","/api/activity/**").authenticated()
                        .requestMatchers("/api/auth","/api/auth/login","/api/auth/refreshToken","/api/auth/logout").permitAll()
                        .requestMatchers("/api/sign-up","/api/sign-up/**").permitAll())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(rs->rs.jwt(Customizer.withDefaults()))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public CommandLineRunner load() {
        return args -> {
            TodoRole role = new TodoRole("USER");
            TodoUser user = new TodoUser("epps@mail.com", "sandra epps",passwordEncoder().encode("password"));
            user.setRole(role);
            userRepository.save(user);


            TodoUser joe = new TodoUser("joe@mail.com", "joe sun",passwordEncoder().encode("password"),new TodoRole("ADMIN"));
            userRepository.save(joe);
        };
    }
}
