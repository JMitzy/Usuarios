/*
 * @file UsuarioController.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,20:18:04
 */
package com.hotelnila.usuarios.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelnila.usuarios.jwt.AuthenticationResponse;
import com.hotelnila.usuarios.jwt.LoginRequest;
import com.hotelnila.usuarios.jwt.RegistroRequest;
import com.hotelnila.usuarios.services.UsuarioService;

import lombok.RequiredArgsConstructor;


/**
 * Controlador que maneja las solicitudes relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	/**
	 * Maneja la solicitud de inicio de sesion de un usuario.
     * @param request Objeto LoginRequest que contiene los detalles de inicio de sesion del usuario.
     * @return ResponseEntity con la respuesta de autenticacion.
	 */
	@PostMapping(value = "login", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    /**
     * Maneja la solicitud de registro de un nuevo usuario.
     * @param request Objeto RegistroRequest que contiene los detalles de registro del usuario.
     * @return ResponseEntity con la respuesta de autenticacion.
     */
    @PostMapping(value = "registro", headers = "X-API-VERSION=1.1.0")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistroRequest request)
    {
        return ResponseEntity.ok(usuarioService.register(request));
    }

}
