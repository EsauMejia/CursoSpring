package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Product;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.CategoryMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

//el uses se usa para que se sepa que al mapear el target category se haga con el mapping ya existente
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mappings({
            @Mapping(source = "idProducto", target = "productId"),
            @Mapping(source = "nombre", target = "name"),
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "precioVenta", target = "price"),
            @Mapping(source = "cantidadStock", target = "stock"),
            @Mapping(source = "estado", target = "active"),
            //al estar relacionada la tabla producto y categoria, ya sea en el mapping de producto o categoria
            //se debe tomar en cuenta el atributo de su relacion, en este caso al ser categoria donde está
            //la funcion mappedBy en su relacion, ese atributo es el que se usa
            @Mapping(source = "categoria", target = "category"),
    })
    Product toProduct(Producto producto);
    //en este caso no se volvería a definir la @Mappings porque es el mismo tipo de mapeo
    List<Product> toProducts(List<Producto> productos);

    //a la inversa
    @InheritInverseConfiguration
    //como en Product no tenemos codigo de barras, se debe tener en cuenta ignorarlo al momento de
    //hacer la conversión a Producto
    @Mapping(target = "codigoBarras", ignore = true)
    Producto toProducto(Product product);

}
