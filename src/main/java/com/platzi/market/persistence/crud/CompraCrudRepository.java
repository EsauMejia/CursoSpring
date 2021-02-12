package com.platzi.market.persistence.crud;

import com.platzi.market.persistence.entity.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//dentro de la operacion diamante entity, el tipo de la llave primaria de esa entity
public interface CompraCrudRepository extends CrudRepository<Compra, Integer> {

    //este query methods está poensado para la clase CompraRepository que tiene el metodo siguiente
    //public Optional<List<Purchase>> getByClient(String clientId){}
    //idCliente se llama el campo en la entity Compra
    Optional<List<Compra>> findByIdCliente(String idCliente);

}
