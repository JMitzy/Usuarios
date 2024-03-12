/*
 * @file UsuarioRepository.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,22:04:36
 */
package com.hotelnila.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelnila.usuarios.entity.Usuario;

/**
 * The Interface UsuarioRepository.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	 
 	/**
 	 * Busca un usuario por su nombre de usuario.
     * 
     * @param username El nombre de usuario del usuario a buscar.
     * @return Un objeto Optional que puede contener el usuario encontrado, 
     * o vacío si no se encuentra ningún usuario con el nombre de usuario proporcionado.
 	 */
 	Optional<Usuario> findByUsername(String username); 
	 
}
