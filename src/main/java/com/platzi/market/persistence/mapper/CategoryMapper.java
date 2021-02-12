package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Category;
import com.platzi.market.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

//notacion que idnica que es un componente spring
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    //como son multiples los datos que se mapearán
    @Mappings({
            //source es la fuente o nombre original del campo de la entity Categoria
            // target es como se llama el atributo en la nueva clase Category
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active"),
    })
    //forma en la cual las categorias se vuelven Category. Se llama mapeadores a esta linea
    Category toCategory(Categoria categoria);

    //de esta forma mapstruct sabe que debe colocar a la inversa los valores de source y target en este mapping
    @InheritInverseConfiguration
    //En la clase categoria el atributo productos se usa para las relaciones, en la interfaz Category no existe
    //asi que se tiene que tener el cuidado de pasar de Category a categoria, ya que a Category le falta ese atributo y debe ignorarlo
    @Mapping(target = "productos", ignore = true)
    //por si se quiere hacer a la inversa
    Categoria toCategoria(Category category);

}
