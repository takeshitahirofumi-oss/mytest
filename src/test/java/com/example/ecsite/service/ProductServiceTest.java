package com.example.ecsite.service;

import com.example.ecsite.entity.Product;
import com.example.ecsite.entity.ProductImage;
import com.example.ecsite.entity.Stock;
import com.example.ecsite.repository.ProductImageRepository;
import com.example.ecsite.repository.ProductRepository;
import com.example.ecsite.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void findAllReturnsProducts() {
        Product p1 = createProduct(1L);
        Product p2 = createProduct(2L);
        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getProductId());
    }

    @Test
    void findByIdReturnsProduct() {
        Product p = createProduct(10L);
        when(productRepository.findById(10L)).thenReturn(Optional.of(p));

        Optional<Product> result = productService.findById(10L);

        assertTrue(result.isPresent());
        assertEquals(10L, result.get().getProductId());
    }

    @Test
    void findFirstImageReturnsMatchedImage() {
        ProductImage imageFor1 = createImage(1L, new byte[]{1, 2});
        ProductImage imageFor2 = createImage(2L, new byte[]{3, 4});
        when(productImageRepository.findAll()).thenReturn(List.of(imageFor1, imageFor2));

        Optional<ProductImage> result = productService.findFirstImage(2L);

        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getProduct().getProductId());
    }

    @Test
    void getTotalStockSumsOnlyTargetProductAndTreatsNullAsZero() {
        Stock stock1 = createStock(1L, 5);
        Stock stock2 = createStock(1L, null);
        Stock stock3 = createStock(2L, 7);
        when(stockRepository.findAll()).thenReturn(List.of(stock1, stock2, stock3));

        int total = productService.getTotalStock(1L);

        assertEquals(5, total);
    }

    private Product createProduct(Long productId) {
        Product p = new Product();
        p.setProductId(productId);
        return p;
    }

    private ProductImage createImage(Long productId, byte[] imageBytes) {
        ProductImage image = new ProductImage();
        image.setProduct(createProduct(productId));
        image.setImage(imageBytes);
        return image;
    }

    private Stock createStock(Long productId, Integer quantity) {
        Stock stock = new Stock();
        stock.setProduct(createProduct(productId));
        stock.setQuantity(quantity);
        return stock;
    }
}
