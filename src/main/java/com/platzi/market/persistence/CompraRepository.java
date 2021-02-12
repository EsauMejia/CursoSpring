package com.platzi.market.persistence;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.repository.PurchaseRepository;
import com.platzi.market.persistence.crud.CompraCrudRepository;
import com.platzi.market.persistence.entity.Compra;
import com.platzi.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//al implementar purchaserepository daba error si no se implementaban los metodos
@Repository
public class CompraRepository implements PurchaseRepository {
    //para inyectar la interfaz en esta clase
    @Autowired
    private CompraCrudRepository compraCrudRepository;

    //como el repositorio debe hablar en terminos del dominio, entonces tambien se inyecta el mapper
    @Autowired
    private PurchaseMapper mapper;

    //aqui se deberia usar el metodo compracrudrepository.findall pero eso devuelve un iterable de Compra y lo que necesitamos
    //es una lista de purchase, asi que se usa el purchasemapper para que los convierta a todos y se castea dentro del topurchases
    //porque este espera una lista de Compra
    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    //para este metodo se utilizará un query methods dentro de CompraCrudRepository, ya que compracrudrepository extiende
    //desde crudrepository y no hay un metodo que se asemeje a lo que queremos
    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        //como debemos hablar en terminos de Compra entonces convertimos el purchase en compra
        Compra compra = mapper.toCompra(purchase);
        //como el save se hace en cascada, entonces debemos estar seguros que
        // la compra conoce sus productos - foreach -  productos saben a que compra pertenecen
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        //retornará el mapear a un purchase singular y guardar la compra que se estaba trabajando en las 2 lineas anteriores
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
