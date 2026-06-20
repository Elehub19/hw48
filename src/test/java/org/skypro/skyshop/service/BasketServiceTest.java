package test.java.org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.product.Product;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    ProductBasket productBasket;

    @Mock
    StorageService storageService;

    @InjectMocks
    BasketService basketService;

    @Test
    void addNonExistingProduct_throwsException() {
        UUID id = UUID.randomUUID();
        when(storageService.getProductById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.addProduct(id));
    }

    @Test
    void addExistingProduct_callsBasketAdd() {
        UUID id = UUID.randomUUID();
        Product p = mock(Product.class);

        when(storageService.getProductById(id)).thenReturn(Optional.of(p));
        when(p.getId()).thenReturn(id);

        basketService.addProduct(id);

        verify(productBasket, times(1)).addProduct(id);
    }

    @Test
    void emptyBasket_returnsEmptyUserBasket() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket basket = basketService.getUserBasket();

        assertTrue(basket.getItems().isEmpty());
    }
}