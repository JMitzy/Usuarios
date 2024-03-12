package com.hotelnila.usuarios.services;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotelnila.usuarios.entity.Rol;
import com.hotelnila.usuarios.entity.Usuario;
import com.hotelnila.usuarios.jwt.AuthenticationResponse;
import com.hotelnila.usuarios.jwt.JwtUtil;
import com.hotelnila.usuarios.jwt.LoginRequest;
import com.hotelnila.usuarios.jwt.RegistroRequest;
import com.hotelnila.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.Builder;



@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRep;

	@Autowired
	private AuthenticationManager autManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
     * Maneja la operación de inicio de sesión para un usuario.
     * 
     * @param request El objeto de solicitud de inicio de sesión que contiene el nombre de usuario y la contraseña.
     * @return Una respuesta de autenticación que contiene el token de acceso si el inicio de sesión es exitoso.
     * @throws AuthenticationException Si la autenticación falla.
     */
	public AuthenticationResponse login(LoginRequest request) {
        autManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails usuario=usuarioRep.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtUtil.createToken(usuario);
        return AuthenticationResponse.builder()
            .token(token)
            .build();

    }
	
	/**
     * Maneja la operación de registro de un nuevo usuario.
     * 
     * @param request El objeto de solicitud de registro que contiene el nombre de usuario, la contraseña y el correo electrónico del nuevo usuario.
     * @return Una respuesta de autenticación que contiene el token de acceso si el registro es exitoso.
     */

    public AuthenticationResponse register(RegistroRequest request) {
        Usuario usuario = Usuario.builder().
             username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .email(request.getEmail())
            .rol(Rol.USER)
            .build();

        usuarioRep.save(usuario);

        return AuthenticationResponse.builder()
            .token(jwtUtil.createToken(usuario))
            .build();
        
    }

}
