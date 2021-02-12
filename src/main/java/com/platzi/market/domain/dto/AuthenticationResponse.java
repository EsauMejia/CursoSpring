package com.platzi.market.domain.dto;

//clase que auxilie el controlador para que envie como respuesta el jwt
public class AuthenticationResponse {
    private String jwt;

    //se le crea un constructor en el que le enviemos directamente el jwt
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
