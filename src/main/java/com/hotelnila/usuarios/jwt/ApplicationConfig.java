/*
 * @file ApplicationConfig.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,20:53:25
 */
package com.hotelnila.usuarios.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hotelnila.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Clase de configuración 
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

    /**
     * Bean para obtener el gestor de autenticacion.
     * @param config La configuración de autenticacion.
     * @return El gestor de autenticacion configurado.
     * @throws Exception Si hay algún error al obtener el gestor de autenticación.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    /**
      * Bean para proporcionar el proveedor de autenticación.
      * @return El proveedor de autenticación configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Bean para proporcionar el codificador de contraseñas.
     * @return El codificador de contraseñas BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para proporcionar el servicio de detalles de usuario.
     * @return El servicio de detalles de usuario configurado.
     */
    @Bean
    public UserDetailsService userDetailService() {
    	return  username ->usuarioRepository.findByUsername(username)
    			.orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));
    }
}
