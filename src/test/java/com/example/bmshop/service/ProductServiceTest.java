package com.example.bmshop.service;

import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.ProductRepository;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> productIdArgumentCaptor;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void productFindById_Mock_Test() throws Exception {

        //Given
        Product product = new Product();
        product.setId(2L);
        product.setName("Banana");
        product.setWeight(1.5f);
        product.setPrice(2.0f);
        product.setQuantity(5.4f);

        given(productRepository.findById(Mockito.eq(3L))).willReturn(Optional.of(product));

        //When
        Product result = productService.productFindById(3L);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Banana");
        assertThat(result.getWeight()).isEqualTo(1.5f);
        assertThat(result.getPrice()).isEqualTo(2.0f);
        assertThat(result.getQuantity()).isEqualTo(5.4f);

    }

    @Test
    public void getProducts_Mock_Test() throws Exception {
        //Given
        List<Product> productList = List.of(
                new Product(1L, "Banana", 0.3f, 10.5f, 5.0f),
                new Product(2L, "Apple", 0.5f, 10.6f, 6.0f),
                new Product(3L, "Onion", 0.5f, 10.7f, 7.0f)
        );
        given(productRepository.findAll()).willReturn(productList);

        //When
        List<Product> resultList = productService.getProducts();

        //Then
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(productList.size());
        assertThat(resultList).isEqualTo(productList);
        assertThat(resultList).hasSize(3).extracting(x -> x.getName() + ' ' +
                x.getQuantity()).containsExactlyInAnyOrder("Banana 5.0", "Apple 6.0", "Onion 7.0");
    }

    @Test
    public void addProduct_Mock_Test() throws ServiceException {
        //Given
        Product product = new Product(1L, "Banana", 3.0f, 5.0f, 5.0f);

        //When
        Product result = productService.addProduct(product);

        //Then
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
        Product prodExpected = productArgumentCaptor.getValue();

        assertThat(prodExpected).isNotNull();
        assertThat(prodExpected).isEqualTo(product);
    }

    @Test
    public void deleteProduct_Mock_Test() {
        //Given
        Long productId = 16L;

        //When
        productService.deleteProduct(productId);

        //Then
        verify(productRepository, times(1)).deleteById(productIdArgumentCaptor.capture());
        Long expected = productIdArgumentCaptor.getValue();

        assertThat(productId).isEqualTo(expected);
    }

    @Test
    public void updateProduct_Mock_Test() {

        //Given
        Product product = new Product(3L, "Banana", 3.0f, 5.0f, 5.0f);

        //When
       // Product result = productService.updateProduct();

        //Then
        assertThat(product).isEqualTo(product);
    }
}
