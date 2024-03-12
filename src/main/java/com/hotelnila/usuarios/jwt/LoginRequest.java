/*
 * @file LoginRequest.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,21:57:00
 */
package com.hotelnila.usuarios.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Representa una solicitud de inicio de sesión con credenciales de usuario.
 */
@Data
@Builder
@AllArgsConstructor
public class LoginRequest {
	
	/** Nombre de usuario proporcionado en la solicitud de inicio de sesion */
	String username;
    
	
    /** Contraseña proporcionada en la solicitud de inicio de sesion. */
    String password; 
}
