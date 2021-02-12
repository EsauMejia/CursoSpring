package com.platzi.market.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
//como se está implementando se deben implementar los métodos automaticamente por el ide
public class PlatziUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //se configura un nuevo usuario con su contrania, el tercer parametro es el conjunto de roles para el usuario
        //como la nuevas contra no se ha encriptado entonces se debe poner la aclaracion noop
        return new User("alejandro","{noop}platzi", new ArrayList<>());
    }
}
