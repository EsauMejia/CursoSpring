package com.platzi.market.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//para activar swagger
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    //se hace un metodo de tipo Docket
    public Docket api(){

        //se especifica que se seleccione el paquete de controller ya que ahi se encentran los endpoint que
        //queremos que se expongan a traves de la documentacion
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.platzi.market.web.controller"))
                .build();
    }


}
