package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//esta anotación le dice a spring que esta clase será un controlador de una api rest
@RestController
//esta anotacion le indica en cual path aceptará las peticiones
@RequestMapping("/products")
public class ProductController {

    //se puede usar, porque ProductService tiene la anotacion de spring de @service
    @Autowired
    //inyectar el servicio
    private ProductService productService;

    //comienzan a listarse los métodos de productservice

    //para verlo expuesto http://localhost:8090/platzi-market/api/products/all
    //se implementan responseentity, que deja tal cual lo que estaba y se agrega lo nuevo
    @GetMapping("/all")
    //descripcion de lo que hace la api
    @ApiOperation("Get all supermarket products")
    //mensaje de respuesta que espera
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<Product>> getAll(){
        //como el método tiene el responseentity se debe tomar en cuenta en el retorno
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    //tiene una notación diferente ya que este método tiene un parametro, por eso se debe tomar en cuenta
    //al momento de exponerlo http://localhost:8090/platzi-market/api/products/49
    @GetMapping("/{id}")
    //antes de implementar los responseentity, se le quita el Optional porque el método getProduct ya cuenta con eso
    //public Optional<Product> getProduct(@PathVariable("id") int productId)
    //y el metodo getproduct al tener Optional se puede recorrer con un map, al usar map se debe controlar lo que sucede si el map no se ejecuta, se usa orelse
    @ApiOperation("Search a product with an ID")
    //se coloca en plural ya que este método en especifico puede devolver 2 mensajes
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    //apiparam sirve para describir el parametro que necesita este metodo
    public ResponseEntity<Product> getProduct(@ApiParam(value = "The id of the product", required = true, example = "7")@PathVariable("id") int productId){
        return productService.getProduct(productId).map(product -> new ResponseEntity<>(product,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //al ser muy similar al metodo anterior que espera un parametro int, se le pone el diferenciador /category
    //al momento de exponerlo http://localhost:8090/platzi-market/api/products/category/1 para ver categoria frutas y verduras
    //aqui tambien se sustituye el Optional por el responseentity
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId){
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //al ser de almacenaje se usa POST, como el producto no viajará en el post, se debe definir que viajará
    //por el cuerpo de la petición @RequestBody
    //en postman debe ser tipo POST http://localhost:8090/platzi-market/api/products/save y
    //en el body se colocan los datos mínimos para hacer la incersión, nos retorna productid 51 para verificarlo
    //si no hay Optional solo se adapta lo que ya se tiene al responsee
    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    //como igual espera un  parametro entero, para evitar ambiguedad se necesita el diferenciador delete
    //para exponer el metodo se coloca http://localhost:8090/platzi-market/api/products/delete/51 y devuelve true
    //en el caso particular, ya no devolveremos un boolean asi que se reemplaza por responseentity
    //antes de los response entity public boolean delete(@PathVariable("id") int productId)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") int productId){
        //antes de los response entity return productService.delete(productId);
        if(productService.delete(productId)){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
