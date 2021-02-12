package com.platzi.market.web.security.filter;

import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//para que se pueda inyectar en otra clase
@Component
//este servirá para atrapar todas las peticiones que recibe la app y verifique si el jwt es correcto
//extiende para algo que tiene spring security para que el filtro se ejecute cada vez que exista una peticion
public class JwtFilterRequest extends OncePerRequestFilter {

    //inyectamos para utilizarlo al verificar el usuario en el jwt
    @Autowired
    private JWTUtil jwtUtil;

    //para inyectar el servicio que hace la autenticacion
    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    //se implementa el metodo que sugiere para que no haya error
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //aqui vamos a verificar que si lo que viene en el encabezado de la peticion es un token y si este es correcto
        //se captura el header autorization
        String authorizationHeader = request.getHeader("Authorization");

        //se verifica que exista el header y que venga con el prefijo bearer que es el que se usa con jwt
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            //se captura desde ls posicion 7 por los espacios de bearer y espacio en blanco
            String jwt = authorizationHeader.substring(7);
            //para verificar el usuario que viene en el jwt
            String username = jwtUtil.extractUsername(jwt);

            //que el usuario exista && verificar que en el contexto no venga una autenticacion para este usuario (no loggeado)
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
                //de esta forma se verificará el usuario que viene en el jwt
                UserDetails userDetails = platziUserDetailsService.loadUserByUsername(username);

                //para verificar que el jwt sea correcto. Se le pasa el token jwt y se pasan tambien los userdetails
                if(jwtUtil.validateToken(jwt, userDetails)){
                    //el null es porque no se usan credenciales especiales y en userDetails.getAuthorities() vienen los roles que puede tener ese usuario
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //para indicarle al authtoken los detalles de la conexion que esta recibiendo
                    //se le envia el request con el fin de evaluar la hora en que sucedio, el navegador que tenia, cual SO utilizó
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //para que en una proxima vez, se evite tener que volver a validarse este filtro del if despues de las &&
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        //para que el filtro sea evaluado
        filterChain.doFilter(request, response);
    }

}
