/*
 * @file SecurityConfig.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,22:02:30
 */
package com.hotelnila.usuarios.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configuracion de seguridad para la aplicacion.
 */
@Configuration
public class SecurityConfig {

	@Autowired
	private AuthenticationProvider authProvider;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	
	/**
	 * Configura la cadena de filtros de seguridad para la aplicacion.
     * 
     * @param httpSecurity El objeto HttpSecurity para configurar la seguridad.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si hay un error al configurar la seguridad.
	 */
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
            .csrf(csrf -> 
                csrf
                .disable())
            .authorizeHttpRequests(authRequest ->
              authRequest
                .requestMatchers("/api/usuarios/**").permitAll()
                .anyRequest().authenticated()
                )
            .sessionManagement(sessionManager->
                sessionManager 
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
            
            
    }
	
}
