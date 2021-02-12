package com.platzi.market.persistence;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import com.platzi.market.persistence.crud.ProductoCrudRepository;
import com.platzi.market.persistence.entity.Producto;
import com.platzi.market.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {

    //se deben colocar la anotacion @Autowired para que el código entienda que no solo por no inicializar los 2
    //objetos ProductoCrudRepository y ProductMapper significa que no están creados, ya que esto podría conllevar
    //con el error nullpointerexception. Es importante saber si ambos objetos cuentan con componentes de Spring
    //ProductoCrudRepository - extends CrudRepository - @NoRepositoryBean public interface CrudRepository
    //ProductMapper - @Mapper(componentModel = "spring", uses = {CategoryMapper.class}) public interface ProductMapper {

    @Autowired
    //llamado de la interfaz
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    //se crea esta variable para ejecutar las conversiones
    private ProductMapper mapper;

    @Override
    //metodo que recupere toda la lista de productos
    public List<Product> getAll(){
        //como el metodo findall devuelve un iterable, se debe castear la lista para que quite el error
        //originalmente, return productoCrudRepository.findAll();
        //return (List<Producto>) productoCrudRepository.findAll();
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        //en productMapper hay un metodo que justo recibe productos y los transforma en products por eso se llama
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        //Como el método espera una lista opcional de products entonces se usa el mapper para convertir los productos en products
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        //debe tenerse cuidado porque esa query devuelve una lista de productos OPtional, asi que asi se deben ir a traer
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        //y ya que no tenemos un método que devuelva una lista de products opcionales, se debe hacer uno por uno
        //con la función lamda con el map. Cada producto pasa por el metodo toProducts a traves de mapper
        return productos.map(prods -> mapper.toProducts(prods));
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        //aqui no se usa toProducts ya que esa retorna una lista y en este caso son registros unicos
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        //inicialmente, da un error porque el metodo save recibe un Producto no un Product
        Producto producto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(producto));
    }

    //public List<Producto> getByCategoria(int idCategoria){
    //    return productoCrudRepository.findByIdCategoriaOrderByNombreAsc(idCategoria);
    //}

    //public Optional<List<Producto>> getEscasos(int cantidad){
        //return productoCrudRepository.findByCantidadStockLessThanAndEstado(cantidad, true);
    //}

    //los siguiente metodos no se declaran en ProductoCrudRepository, ya que esos vienen por defecto en esa interfaz
    //public Optional<Producto> getProducto(int idProducto){
    //    return productoCrudRepository.findById(idProducto);
    //}

    //el tipo de lo que va a recibir - nombre del metodo - parametros
    //public Producto save(Producto producto){
    //    return productoCrudRepository.save(producto);
    //}

    //como este metodo no devuelve nada, solamente se coloca la anotacion de @Override
    //solo se cambia, opcionalmente, el nombre del parametro para que coincida más con ProductRepository
    @Override
    public void delete(int productId){
        productoCrudRepository.deleteById(productId);
    }

}
