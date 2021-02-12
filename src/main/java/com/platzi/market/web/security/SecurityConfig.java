package com.platzi.market.web.security;

import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.filter.JwtFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//esta clase servirá para indicarle al proyecto que estos son los usuarios validos para entrar
@EnableWebSecurity
//esta clase va a extender la clase que ya viene en security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //ya que necesitamos pasarle las credenciales que queremos, entonces inyectamos la clase service PlatziUserDetailsService
    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;

    //para el filter before
    @Autowired
    private JwtFilterRequest jwtFilterRequest;

//se usa el ide - code - generate - override -  se elije el configure que recibe authentication managerbuilder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //linea autogenerado
        //super.configure(auth);
        auth.userDetailsService(platziUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //que deshabilite las peticiones cruzadas y autorice las peticiones y en antmatchers se especifica
        //lo que si queremos permitir, que ene ste caso es el postMapping configurado en el AuthController
        //es decir, que todas peticiones que acaben en /authenticate se permitirán
        //cualquier otra peticion necesita ser autenticada
        http.csrf().disable().authorizeRequests().antMatchers("/**/authenticate").permitAll()
                .anyRequest().authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //luego del and se hace para decirle que ese filtro será encargado de recibir todas las peticiones y procesarlas
        //el sessionpoliciy es para indicar que la session utilizada en la app será stateless, es decir una sesion sin estado
        //porque los jwt se encargaran de controlar cada peticion en particular sin manejar una sesion como tal

        //que agregue un filtro antes, siendo el filtro el jwfilterrequest y de tipo usernamepassword
        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    //en authcontroller se usa authenticationmanager entonces aqui se debe sobreescribir
    @Override
    //con esto le decimos al proyecto que sea siempre este quien gestione las autenticaciones
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
