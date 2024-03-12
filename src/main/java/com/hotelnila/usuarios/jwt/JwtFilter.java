/*
 * @file JwtFilter.java;
 * @Autor (c)2024 JhenniferMendoza
 * @Created 11 mar. 2024,21:02:22
 */
package com.hotelnila.usuarios.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Filtro para procesar las solicitudes HTTP y validar los tokens JWT.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserDetailsService detUsuService;
	

	/**
	 * Metodo principal para filtrar las solicitudes HTTP y validar los tokens JWT.
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP saliente.
     * @param filterChain La cadena de filtros para la solicitud.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
	 */
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
        final String token = getTokenFromRequest(request);
        final String username;

        if (token==null)
        {
            filterChain.doFilter(request, response);
            return;
        }

        username=jwtUtil.extractNombreUsuario(token);

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=detUsuService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails))
            {
                UsernamePasswordAuthenticationToken autentiToken= new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

                autentiToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(autentiToken);
            }

        }
        
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token de autorización de la solicitud HTTP.
     * @param request La solicitud HTTP entrante.
     * @return El token JWT extraído de la solicitud, o null si no se encuentra.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String autentiHeader=request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(autentiHeader) && autentiHeader.startsWith("Bearer "))
        {
            return autentiHeader.substring(7);
        }
        return null;
    }

	
}
