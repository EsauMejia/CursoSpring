package com.platzi.market.web.controller;

import com.platzi.market.domain.dto.AuthenticationRequest;
import com.platzi.market.domain.dto.AuthenticationResponse;
import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//controlará el envio de datos y la captura del jwt
//notacion del controlador
@RestController
@RequestMapping("/auth")
public class AuthController {

    //se inyecta porque spring ya lo trae
    @Autowired
    private AuthenticationManager authenticationManager;

    //se inyecta el servicio que es donde esta configurado el usuario y password que deseamos
    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    //inyectamos nuestra clase creada con anterioridad
    @Autowired
    private JWTUtil jwtUtil;

    //se define la url con que se podrá acceder a este metodo
    @PostMapping("/authenticate")
    //un metodo que reciba un authenticationrequest y devuelva un authentication response
    //como envia por POST es necesario colocar RequestBody en el parametro
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request){
        //el try intentara hacer la autenticacion, de no hacerse se lanza el error
        try {
            //a esta peticion se le configura como parametro el usuario que debe validar, con su usuario y contra
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = platziUserDetailsService.loadUserByUsername(request.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            //se crea un nu7evo autenticationresponse mandandole al constructor el jwt que sale de este metodo
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        }catch(BadCredentialsException e){
            //este mensaje indica que no se hizo bien el loggeo de sesion
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        }


    }
}
