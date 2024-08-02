package com.ecommecer.example.service;

import com.ecommecer.example.entity.Product;
import com.ecommecer.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }
    /* boolean or Boolean?
    como quero trabalhar apenas com a representação bi-estadual(true or false) boolean é a melhor escolha neste
    momento.
     */
    public boolean deleteProduct(Long id){
        if (productRepository.existsById(id)){
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    /*
    public void deleteById(Long id){
        logger.info("deleting product by id {}", id);
        return productRepository.deleteById(id);
    }
     */
}
