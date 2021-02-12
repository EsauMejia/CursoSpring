package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

//se debe poner para que sea inyectado en el proceso, de lo contrartio el proceso no se inicia
@Component
public class JWTUtil {

    private static final String KEY = "pl4tz1";

    public String generateToken(UserDetails userDetails){
        //al constructor se le manda el usuario extrayendole el username. la fecha en que se creó
        //.la fecha en que expirará, la fecha actual en milisegundos dandole 10h de vida al token
        //.firma signWith(algoritmo, key key) y se le debe enviar la llave que propia que se usa para encriptar
        //.finalmente se compacta para crear el token
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    //metodo que validara el token
    public boolean validateToken(String token, UserDetails userDetails){
        //se verifica que el usuario de la peticion sea el mismo que el que viene en el token, ademas
        //preguntamos si no ha expirado el token
        //no se valida que venga bien firmado el token ya que el metodo getclaims se encargará de validar
        //y si no viene bien entonces retornará un forbbiden y ni sacará el body del token, por ende se detendrá proceso y ni llegará a la validacion
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    //metodo que ayude a extrar el user dentro del token
    public String extractUsername(String token){
        //de los objetos del jwt se obtiene el subject que es donde viene el user
        return getClaims(token).getSubject();
    }

    //metodo que evalua si el token ya expiró
    public boolean isTokenExpired(String token){
        //al sacar el objeto de la fecha de expiracion y resulta ser anterior a la fechaa ctual retornará un true
        //si no está antes de la fecha actual, aún será un token valido
        return getClaims(token).getExpiration().before(new Date());

    }

    //metodo auxiliar
    //claims es la palabra que se refiere a los objetos que trae el jwt
    private Claims getClaims(String token){
        //un parser que tiene la firma basada en el key y cuando sea verificado esto se obtendran
        //todos los objetos en el jwt en el cuerpo de este
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }

}
