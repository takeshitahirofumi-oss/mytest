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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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

        when(userRepository.findByUserId("testuser")).thenReturn(null);
        when(areaRepository.findAll()).thenReturn(List.of());
        when(warehouseRepository.findAll()).thenReturn(List.of());
        when(categoryRepository.findAll()).thenReturn(List.of());
        when(productRepository.findAll()).thenReturn(List.of());
        when(productImageRepository.findAll()).thenReturn(List.of());
        when(stockRepository.findAll()).thenReturn(List.of());
        when(areaRepository.save(any(Area.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(warehouseRepository.save(any(Warehouse.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productImageRepository.save(any(ProductImage.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(stockRepository.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

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
        verify(productRepository, times(2)).save(productCaptor.capture());
        Product savedProduct = productCaptor.getAllValues().get(0);
        assertEquals("サンプルテレビ", savedProduct.getProductName());
        assertEquals(50000, savedProduct.getPrice());
        assertNotNull(savedProduct.getCategory());

        Product savedProduct2 = productCaptor.getAllValues().get(1);
        assertEquals("サンプル冷蔵庫", savedProduct2.getProductName());
        assertEquals(80000, savedProduct2.getPrice());
        assertNotNull(savedProduct2.getCategory());

        ArgumentCaptor<ProductImage> imageCaptor = ArgumentCaptor.forClass(ProductImage.class);
        verify(productImageRepository, times(2)).save(imageCaptor.capture());
        ProductImage savedImage = imageCaptor.getAllValues().get(0);
        assertEquals(1, savedImage.getImageNo());
        assertNotNull(savedImage.getProduct());
        assertNotNull(savedImage.getImage());
        assertTrue(savedImage.getImage().length > 0);

        ProductImage savedImage2 = imageCaptor.getAllValues().get(1);
        assertEquals(1, savedImage2.getImageNo());
        assertNotNull(savedImage2.getProduct());
        assertNotNull(savedImage2.getImage());
        assertTrue(savedImage2.getImage().length > 0);

        ArgumentCaptor<Stock> stockCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockRepository, times(2)).save(stockCaptor.capture());
        Stock savedStock = stockCaptor.getAllValues().get(0);
        assertEquals(10, savedStock.getQuantity());
        assertNotNull(savedStock.getProduct());
        assertNotNull(savedStock.getWarehouse());

        Stock savedStock2 = stockCaptor.getAllValues().get(1);
        assertEquals(5, savedStock2.getQuantity());
        assertNotNull(savedStock2.getProduct());
        assertNotNull(savedStock2.getWarehouse());
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

        User existingUser = new User();
        existingUser.setUserId("testuser");
        when(userRepository.findByUserId("testuser")).thenReturn(existingUser);

        Area area = new Area();
        area.setAreaName("東日本");

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseName("東京倉庫");
        warehouse.setArea(area);

        Category category = new Category();
        category.setCategoryName("家電");

        Product product1 = new Product();
        product1.setProductName("サンプルテレビ");
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setProductName("サンプル冷蔵庫");
        product2.setCategory(category);

        ProductImage image1 = new ProductImage();
        image1.setProduct(product1);
        image1.setImageNo(1);

        ProductImage image2 = new ProductImage();
        image2.setProduct(product2);
        image2.setImageNo(1);

        Stock stock1 = new Stock();
        stock1.setProduct(product1);
        stock1.setWarehouse(warehouse);

        Stock stock2 = new Stock();
        stock2.setProduct(product2);
        stock2.setWarehouse(warehouse);

        when(areaRepository.findAll()).thenReturn(List.of(area));
        when(warehouseRepository.findAll()).thenReturn(List.of(warehouse));
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(productImageRepository.findAll()).thenReturn(List.of(image1, image2));
        when(stockRepository.findAll()).thenReturn(List.of(stock1, stock2));

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
