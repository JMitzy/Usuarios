/*
 * @file AuthenticationResponse.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,22:20:28
 */
package com.hotelnila.usuarios.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la respuesta de autenticación enviada como resultado 
 * de una operación de inicio de sesión o registro.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
	
	/** El token de acceso generado para el usuario autenticado. */
	String token; 
}
