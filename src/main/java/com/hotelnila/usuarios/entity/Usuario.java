/*
 * @file Usuario.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,20:38:57
 */
package com.hotelnila.usuarios.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Clase que representa a un usuario en el sistema.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})

public class Usuario implements UserDetails {
	
	/** Identificador único del usuario. */
	@Id
	@GeneratedValue
	Integer id;
	
	/** Nombre de usuario del usuario. */
	@Basic
	@Column(nullable = false)
	String username;
	
	/** Dirección de correo electrónico del usuario. */
	@Email
	@NotBlank
	String email;
	
	/** Contraseña del usuario. */
	String password;
	
	/** Rol del usuario en el sistema. */
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	/**
	 * Obtiene los roles asociados al usuario.
     * @return Una coleccion de autoridades otorgadas al usuario.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(rol.name()));
	}
	
	/**
	 * Indica si la cuenta del usuario ha expirado.
     * @return Siempre devuelve true, lo que significa que la cuenta nunca expira.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/**
	* Indica si la cuenta del usuario esta bloqueada.
     * @return Siempre devuelve true, lo que significa que la cuenta nunca esta bloqueada.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
	 * Indica si las credenciales del usuario han expirado.
     * @return Siempre devuelve true, lo que significa que las credenciales nunca expiran.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
	 * Indica si el usuario esta habilitado.
     * @return Siempre devuelve true, lo que significa que el usuario siempre esta habilitado.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
