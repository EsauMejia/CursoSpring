package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.PurchaseItem;
import com.platzi.market.persistence.entity.ComprasProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

//esta anotacion componentmodel es para poderlo inyectar posteriormente desde otros lugares
//como en el script se usa el campo producto se debe hacer referencia a la clase que usará cuando se trabaje con producto
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    //como serán listados varios, se usan llaves
    @Mappings({
            //sorce es la fuente de donde viene el dato original, target es la nueva forma en que nos referiremos a este
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            //este se suprime, ya que no es necesario porque se llaman igual en ambos lados
            //@Mapping(source = "total", target = "total"),
            @Mapping(source = "estado", target = "active")
    })
    //este es el conversor, que convertirá los datos de ComprasProducto en PurchaseItem
    PurchaseItem toPurchaseItem(ComprasProducto producto);

    //se usa esta anotacion por la necesidad de hacer el cambio a la inversa. Es decir, de purchaseitem a comprasproducto
    @InheritInverseConfiguration
    //En base a ComprasProducto, si un Purchase se quiere convertir en esta, entonces hay una serie de campos que se deben ignorar
    //ya que no tienen un equivalente en la clase Purchase que es la que se quiere convertir a ComprasProducto
    //exactamnete, los que faltaban son los de la llave foranea y campos que sirven para hacer las relaciones
    @Mappings({
            @Mapping(target = "id.idCompra", ignore = true),
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "producto", ignore = true)
    })
    ComprasProducto toComprasProducto(PurchaseItem item);

}
