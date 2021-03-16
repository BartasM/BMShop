package com.example.bmshop.service;

import com.example.bmshop.dto.BasketPositionDTO;
import com.example.bmshop.dto.BasketPositionModDTO;
import com.example.bmshop.entity.Basket;
import com.example.bmshop.entity.BasketPosition;
import com.example.bmshop.entity.Product;
import com.example.bmshop.repository.BasketPositionRepository;
import com.example.bmshop.repository.BasketRepository;
import com.example.bmshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @InjectMocks
    BasketService basketService;

    @Mock
    BasketRepository basketRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    BasketPositionRepository basketPositionRepository;

    @Captor
    ArgumentCaptor<Basket> basketArgumentCaptor;

    @Captor
    ArgumentCaptor<BasketPosition> basketPositionArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    @Test
    public void getBasketsTest() {
        //Given
        Basket basketTest = new Basket(3L);
        List<Basket> basketList = List.of(basketTest);
        given(basketRepository.findAll()).willReturn(basketList);

        //When
        List<Basket> basketListResut = basketService.getBaskets();

        //Then
        assertThat(basketList).isEqualTo(basketListResut);
    }

    @Test
    public void getBasketByIdTest() {
        //Given
        Long basketId = 2L;
        Basket basketTest = new Basket(2L);
        given(basketRepository.findById(basketId)).willReturn(Optional.of(basketTest));

        //When
        Basket basketResult = basketService.getBasketById(basketId);

        //Then
        assertThat(basketResult).isNotNull();
        assertThat(basketResult).isEqualTo(basketTest);
        assertThat(basketResult.getId()).isEqualTo(basketTest.getId());
    }

    @Test
    public void addBasketPostionTest_WithQuantityMethod() {
        //Given
        Long productId = 2L;
        Product productTest = new Product(productId);

        Long basketPositionId = 4L;
        BasketPosition basketPositionTest = new BasketPosition(basketPositionId);
        basketPositionTest.setProductId(productTest);
        basketPositionTest.setQuantity(10);
        int quantity = 20;

        Long basketId = 3L;
        Basket basketTest = new Basket(basketId);

        Set<BasketPosition> positionSet = new HashSet();
        positionSet.add(basketPositionTest);

        basketTest.setBasketCreatedSet(positionSet);
        basketPositionTest.setBasket(basketTest);

        BasketPositionDTO basketPositionDTOTest = new BasketPositionDTO();
        basketPositionDTOTest.setBasketId(3L);
        basketPositionDTOTest.setProductId(2L);
        basketPositionDTOTest.setQuantity(20);

        given(basketRepository.findById(basketPositionDTOTest.getBasketId()))
                .willReturn(Optional.of(basketTest));
        given(productRepository.findById(basketPositionDTOTest.getProductId()))
                .willReturn(Optional.of(productTest));
        given(basketPositionRepository.findById(basketPositionTest.getBasketPositionId()))
                .willReturn(Optional.of(basketPositionTest));

        //When
        basketService.addBasketPosition(basketPositionDTOTest);

        //Then
        verify(basketPositionRepository, times(1))
                .saveAndFlush(basketPositionArgumentCaptor.capture());
        BasketPosition basketPositionResult = basketPositionArgumentCaptor.getValue();

        assertThat(basketPositionTest.getProductId()).isEqualTo(basketPositionResult.getProductId());
        assertThat(basketPositionDTOTest.getBasketId()).isEqualTo(basketPositionResult.getBasket().getId());
        assertThat(basketPositionResult.getQuantity()).isEqualTo(30);
    }

    @Test
    public void addBasketPostionTest_Exception() {
        //Given
        Long productId = 2L;
        Product productTest = new Product(productId);

        Long basketPositionId = 4L;
        BasketPosition basketPositionTest = new BasketPosition(basketPositionId);
        basketPositionTest.setProductId(productTest);
        basketPositionTest.setQuantity(10);
        int quantity = 20;

        Long basketId = 3L;

        Basket basketTest = new Basket(basketId);

        basketPositionTest.setBasket(basketTest);

        BasketPositionDTO basketPositionDTOTest = new BasketPositionDTO();
        basketPositionDTOTest.setBasketId(3L);
        basketPositionDTOTest.setProductId(2L);
        basketPositionDTOTest.setQuantity(20);

        given(basketRepository.findById(basketPositionDTOTest.getBasketId()))
                .willReturn(Optional.of(basketTest));
        given(productRepository.findById(basketPositionDTOTest.getProductId()))
                .willReturn(Optional.of(productTest));

        //When
        basketService.addBasketPosition(basketPositionDTOTest);

        //Then
        verify(basketPositionRepository, times(1))
                .saveAndFlush(basketPositionArgumentCaptor.capture());
        BasketPosition basketPositionResult = basketPositionArgumentCaptor.getValue();

        assertThat(basketPositionTest.getProductId()).isEqualTo(basketPositionResult.getProductId());
        assertThat(basketPositionDTOTest.getBasketId()).isEqualTo(basketPositionResult.getBasket().getId());
        assertThat(basketPositionResult.getQuantity()).isEqualTo(20);
    }

    @Test
    public void deleteBasketTest() {
        //Given
        Long basketId = 5L;

        //When
        basketService.deleteBasket(basketId);

        //Then
        verify(basketRepository, times(1)).deleteById(longArgumentCaptor.capture());
        Long result = longArgumentCaptor.getValue();

        assertThat(5L).isEqualTo(result);
    }

    @Test
    public void updateBasketPosition() {
        //Given
        Long basketPositionId = 1L;
        BasketPosition basketPositionTest = new BasketPosition(basketPositionId);

        int quantity = 20;
        BasketPositionModDTO basketPositionModDTOTest = new BasketPositionModDTO();
        basketPositionModDTOTest.setQuantity(quantity);

        given(basketPositionRepository.findById(basketPositionId)).willReturn(Optional.of(basketPositionTest));
        //When
        basketService.updateBasketPosition(basketPositionId, basketPositionModDTOTest);

        //Then
        verify(basketPositionRepository, times(1)).save(basketPositionArgumentCaptor.capture());
        BasketPosition basketPositionResult = basketPositionArgumentCaptor.getValue();

        assertThat(basketPositionTest.getBasketPositionId()).isEqualTo(basketPositionResult.getBasketPositionId());
    }

}
