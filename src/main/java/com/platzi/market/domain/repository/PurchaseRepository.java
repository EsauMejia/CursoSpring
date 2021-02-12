package com.platzi.market.domain.repository;

import com.platzi.market.domain.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {

    //una vez estan declarados estos metodos en PurchaseService se pintan de amarillo aqui

    //metodo que devolverá todas las compras
    List<Purchase> getAll();

    //metodo que devolverá una compra de cliente en específico
    //puede ocurrir que se consulte un cliente que no tenga compras, asi que se usará un Optional en ese caso
    Optional<List<Purchase>> getByClient(String clientId);

    //metodo para guardar una compra
    Purchase save(Purchase purchase);

}
