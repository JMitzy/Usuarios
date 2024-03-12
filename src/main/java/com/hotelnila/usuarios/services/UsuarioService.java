/*
 * @file UsuarioService.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,22:07:26
 */
package com.hotelnila.usuarios.services;


import com.hotelnila.usuarios.jwt.AuthenticationResponse;
import com.hotelnila.usuarios.jwt.LoginRequest;
import com.hotelnila.usuarios.jwt.RegistroRequest;

/**
 * Interfaz que define los metodos para manejar las operaciones relacionadas con los usuarios
 */
public interface UsuarioService {
	
	/**
	 * Maneja la operación de inicio de sesión para un usuario.
	 * 
	 * @param request El objeto de solicitud de inicio de sesión que contiene el nombre de usuario y la contraseña.
	 * @return Una respuesta de autenticación que contiene el token de acceso si el inicio de sesión es exitoso.
*/	 
	AuthenticationResponse login(LoginRequest request) ;
	
	/**
	 * Maneja la operacion de registro de un nuevo usuario.
     * 
     * @param request El objeto de solicitud de registro que contiene el nombre de usuario, la contraseña y el correo electronico del nuevo usuario.
     * @return Una respuesta de autenticacion que contiene el token de acceso si el registro es exitoso.
	 */
	AuthenticationResponse register(RegistroRequest request);
	

}
