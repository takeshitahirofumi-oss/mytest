package com.example.ecsite.config;

import com.example.ecsite.entity.Area;
import com.example.ecsite.entity.Category;
import com.example.ecsite.entity.Product;
import com.example.ecsite.entity.ProductImage;
import com.example.ecsite.entity.Stock;
import com.example.ecsite.entity.User;
import com.example.ecsite.entity.Warehouse;
import com.example.ecsite.repository.AreaRepository;
import com.example.ecsite.repository.CategoryRepository;
import com.example.ecsite.repository.ProductImageRepository;
import com.example.ecsite.repository.ProductRepository;
import com.example.ecsite.repository.StockRepository;
import com.example.ecsite.repository.UserRepository;
import com.example.ecsite.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.CommandLineRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DataLoaderTest {

    @Test
    void loadDataCreatesSampleDataWhenRepositoriesAreEmpty() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductImageRepository productImageRepository = mock(ProductImageRepository.class);
        StockRepository stockRepository = mock(StockRepository.class);
        WarehouseRepository warehouseRepository = mock(WarehouseRepository.class);
        AreaRepository areaRepository = mock(AreaRepository.class);

        when(userRepository.count()).thenReturn(0L);
        when(productRepository.count()).thenReturn(0L);

        DataLoader dataLoader = new DataLoader();
        CommandLineRunner runner = dataLoader.loadData(
                userRepository,
                categoryRepository,
                productRepository,
                productImageRepository,
                stockRepository,
                warehouseRepository,
                areaRepository
        );

        runner.run();

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("testuser", savedUser.getUserId());
        assertTrue(savedUser.getPassword().startsWith("{bcrypt}"));
        assertTrue(savedUser.getPassword().length() > "{bcrypt}".length());

        ArgumentCaptor<Area> areaCaptor = ArgumentCaptor.forClass(Area.class);
        verify(areaRepository).save(areaCaptor.capture());
        assertEquals("東日本", areaCaptor.getValue().getAreaName());

        ArgumentCaptor<Warehouse> warehouseCaptor = ArgumentCaptor.forClass(Warehouse.class);
        verify(warehouseRepository).save(warehouseCaptor.capture());
        Warehouse savedWarehouse = warehouseCaptor.getValue();
        assertEquals("東京倉庫", savedWarehouse.getWarehouseName());
        assertNotNull(savedWarehouse.getArea());

        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryCaptor.capture());
        assertEquals("家電", categoryCaptor.getValue().getCategoryName());

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertEquals("サンプルテレビ", savedProduct.getProductName());
        assertEquals(50000, savedProduct.getPrice());
        assertNotNull(savedProduct.getCategory());

        ArgumentCaptor<ProductImage> imageCaptor = ArgumentCaptor.forClass(ProductImage.class);
        verify(productImageRepository).save(imageCaptor.capture());
        ProductImage savedImage = imageCaptor.getValue();
        assertEquals(1, savedImage.getImageNo());
        assertNotNull(savedImage.getProduct());
        assertNotNull(savedImage.getImage());
        assertTrue(savedImage.getImage().length > 0);

        ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockRepository).save(stockCaptor.capture());
        Stock savedStock = stockCaptor.getValue();
        assertEquals(10, savedStock.getQuantity());
        assertNotNull(savedStock.getProduct());
        assertNotNull(savedStock.getWarehouse());
    }

    @Test
    void loadDataSkipsInsertWhenDataAlreadyExists() throws Exception {
        UserRepository userRepository = mock(UserRepository.class);
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductImageRepository productImageRepository = mock(ProductImageRepository.class);
        StockRepository stockRepository = mock(StockRepository.class);
        WarehouseRepository warehouseRepository = mock(WarehouseRepository.class);
        AreaRepository areaRepository = mock(AreaRepository.class);

        when(userRepository.count()).thenReturn(1L);
        when(productRepository.count()).thenReturn(1L);

        DataLoader dataLoader = new DataLoader();
        CommandLineRunner runner = dataLoader.loadData(
                userRepository,
                categoryRepository,
                productRepository,
                productImageRepository,
                stockRepository,
                warehouseRepository,
                areaRepository
        );

        runner.run();

        verify(userRepository, never()).save(any(User.class));
        verify(areaRepository, never()).save(any(Area.class));
        verify(warehouseRepository, never()).save(any(Warehouse.class));
        verify(categoryRepository, never()).save(any(Category.class));
        verify(productRepository, never()).save(any(Product.class));
        verify(productImageRepository, never()).save(any(ProductImage.class));
        verify(stockRepository, never()).save(any(Stock.class));
    }
}
