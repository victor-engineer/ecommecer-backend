package com.ecommecer.example;

import com.ecommecer.example.entity.Product;
import com.ecommecer.example.repository.ProductRepository;
import com.ecommecer.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    //cria um mock
    @Mock
    private ProductRepository productRepository;

    // injeta o mock criado no "productService"
    @InjectMocks
    private ProductService productService;

    //verifica os metodos antes de cada teste.
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllProduct() {
        Product productOne = new Product(1L, "product 1", 10.0);//implementar um argumento
        Product productTwo = new Product(2l, "product 2", 20.0);//implementar um argumento
        List<Product> products = Arrays.asList(productOne, productTwo);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProduct();

        assertEquals(2, result.size());
        assertEquals("product 1", result.get(0).getNome());
        assertEquals("product 2", result.get(1).getNome());
    }

    @Test
    public void testGetProductById_productExist() {
        Product product1 = new Product(1L, "PRODUTO 1", 10.0);


        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        //chamar o metodo que será testado;
        Product result = productService.getProductById(1L);

        //verificar se o resultado é o esperado;
        assertEquals(product1, result);
    }

    @Test
    public void testGetProductById_doesNotExist() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(1L);

        assertNull(result);
    }

    @Test
    public void testCreateProduct() {

        Product product1 = new Product(1L, "product 1", 10L);

        when(productRepository.save(product1)).thenReturn(product1);

        Product result = productService.createProduct(product1);

        assertEquals(product1, result);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    public void testdeleteProductById_productExist() {
        Long id = 1L;

        //se o produto existir, ele vai retorna verdadeiro.
        when(productRepository.existsById(id)).thenReturn(true);

        //saber se deletou
        boolean result = productService.deleteProduct(id);

        //Verifica se o resultado do método deleteProduct é true.
        assertTrue(result);
        verify(productRepository,  times(1)).deleteById(id);
    }

    // verificar o comportamento do metodo vse o id não existe;
    @Test
    public void testDeleteProductById_ProductDoesNotExist() {
        Long id = 2L;

        //Configura o mock para retornar falso quando verificar a existência do produto
        when(productRepository.existsById(id)).thenReturn(false);

        //Chama o método deleteProduct
        productService.deleteProduct(id);

        //Verifica se o método deleteById não foi chamado
        verify(productRepository, never()).deleteById(id);
    }

    //quando occore uma exceção, verificar o comprotamento do metodo
    @Test
    public void testDeleteProduct_ExceptionDuringDeletion() {
        Long id = 3L;

        when(productRepository.existsById(id)).thenReturn(true);
        doThrow(new RuntimeException()).when(productRepository).deleteById(id);

        boolean result = productService.deleteProduct(id);

        assertFalse(result);
        verify(productRepository, times(1)).deleteById(id);
    }
}
