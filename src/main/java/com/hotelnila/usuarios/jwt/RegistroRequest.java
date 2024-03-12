/*
 * @file RegistroRequest.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,21:59:06
 */
package com.hotelnila.usuarios.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * Representa una solicitud de registro con credenciales de usuario.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {
	
	/**  Nombre de usuario proporcionado en la solicitud de registro. */
	String username;
    
    /** Contraseña proporcionada en la solicitud de registro. */
    String password;
    
    /**Direccion de correo electronico proporcionada en la solicitud de registro.*/
    String email;
}
