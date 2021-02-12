package com.platzi.market.domain.service;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    //se puede utilizar autowired, ya que productrepository no tiene componentes de spring pero su implementacion
    //que es producto repository si
    @Autowired
    private ProductRepository productRepository;

    //este servicio habla en términos del dominio, la conversión la hace ProductoRepository y el servicio desconoce
    //las operaciones que ahí se hacen

    //cada vez que un metodo es declarado aqui, en la interfaz ProductRepository se pintan de amarillo los que ya están
    public List<Product> getAll(){
        return productRepository.getAll();
    }

    public Optional<Product> getProduct(int productId){
        return productRepository.getProduct(productId);
    }

    public Optional <List<Product>> getByCategory(int categoryId){
        return productRepository.getByCategory(categoryId);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public boolean delete(int productId){
        //el metodo getProduct se coloca en amarillo porque posee un llamado en esta misma clase

        //opcion1
        return getProduct(productId).map(product -> {
            productRepository.delete(productId);
            return true;
        }).orElse(false);

        //opcion2
        /*
        if(getProduct(productId).isPresent()){
            productRepository.delete(productId);
            return true;
        }
        else{
            return false;
        }
        */
    }



}
