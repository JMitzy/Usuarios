/*
 * @file JwtUtil.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,21:07:55
 */
package com.hotelnila.usuarios.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 *Clase de utilidad para la manipulación de tokens JWT.
 *
 */
@Service
public class JwtUtil {
	
	/** The Constant SECRET_KEY. */
	private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
	
	/**
	 * Verifica si un token JWT ha expirado.
	 * 
     * @param token El token JWT a verificar.
     * @return true si el token ha expirado, false de lo contrario.
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration (token).before(new Date());
	}
	
	/**
	 * Extrae la fecha de expiracion de un token JWT.
	 * 
     * @param token El token JWT del que se extraera la fecha de expiracion.
     * @return La fecha de expiracion del token.
	 */
	public Date extractExpiration(String token) {
		return extractClaims(token,Claims::getExpiration);
	}

	/**
	 * Extrae el nombre de usuario del sujeto del token JWT.
	 * 
     * @param token El token JWT del que se extraerá el nombre de usuario.
     * @return El nombre de usuario extraído del token.
	 */
	public String extractNombreUsuario(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	
	/**
	 * Extrae las reclamaciones del token JWT.
	 * 
     * @param token El token JWT del que se extraerán las reclamaciones.
     * @param claimsResolver El resolvedor de reclamaciones que se utilizará para extraer las reclamaciones específicas.
     * @param <T> El tipo de las reclamaciones que se extraerán.
     * @return Las reclamaciones extraídas del token.
	 */
	public <T> T extractClaims(String token,Function<Claims,T>claimsResolver) {
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Extrae todas las reclamaciones del token JWT.
	 * 
     * @param token El token JWT del que se extraerán todas las reclamaciones.
     * @return Todas las reclamaciones extraídas del token.
	 */
	public Claims extractAllClaims(String token) {
		
		 return Jwts.parserBuilder()
		            .setSigningKey(getKey())
		            .build()
		            .parseClaimsJws(token)
		            .getBody();
	}
	
	/**
	 * Crea un token JWT para el usuario especificado.
	 * 
     * @param usuario Los detalles del usuario para los que se creará el token JWT.
     * @return El token JWT creado.
	 */
	public String createToken(UserDetails usuario) {
		return createToken(new HashMap<>(),usuario);
	}

	/**
	 * Crea un token JWT con reclamaciones personalizadas para el usuario especificado.
	 * 
     * @param claims Las reclamaciones personalizadas que se agregarán al token JWT.
     * @param usuario Los detalles del usuario para los que se creará el token JWT.
     * @return El token JWT creado con las reclamaciones personalizadas.
	 */
	private String createToken(Map<String, Object> claims, UserDetails usuario) {
		
		return Jwts.builder()
				.setClaims(claims).
				setSubject(usuario.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date (System.currentTimeMillis()+100*60*60*10))
				.signWith(getKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	/**
	 * Obtiene la clave utilizada para firmar los tokens JWT.
	 * 
     * @return La clave utilizada para firmar los tokens JWT.
	 */
	private Key getKey() {
		 byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
	     return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Valida un token JWT para los detalles de usuario especificados.
	 * 
     * @param token El token JWT que se validara.
     * @param userDetails Los detalles de usuario con los que se comparara el token JWT.
     * @return true si el token JWT es valido para los detalles de usuario especificados, false de lo contrario.
	 */
	public Boolean validateToken(String token,UserDetails userDetails) {
		final String username=extractNombreUsuario(token);
		return (username.equals(userDetails.getUsername())&& ! isTokenExpired(token));
	}
}
