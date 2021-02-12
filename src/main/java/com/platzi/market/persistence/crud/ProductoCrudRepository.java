package com.platzi.market.persistence.crud;

import com.platzi.market.persistence.entity.Producto;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository <Producto, Integer> {

    //@Query(value = "SELECT * FROM productos WHERE id_categoria = ?",nativeQuery = true)
    //List<Producto> getCategoria(Integer id_categoria);

    //implementando querys methods
    List<Producto> findByIdCategoriaOrderByNombreAsc(Integer id_categoria);

    //traera las cantidades que tengan un stock menor al definido y que tengan un estado en especifico
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);


}
