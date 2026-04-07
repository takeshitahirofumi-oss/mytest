package com.example.ecsite.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class EntityModelTest {

    @Test
    void areaGettersAndSettersWork() {
        Area area = new Area();
        area.setAreaId(1L);
        area.setAreaName("東日本");

        Warehouse warehouse = new Warehouse();
        List<Warehouse> warehouses = List.of(warehouse);
        area.setWarehouses(warehouses);

        assertEquals(1L, area.getAreaId());
        assertEquals("東日本", area.getAreaName());
        assertSame(warehouses, area.getWarehouses());
    }

    @Test
    void categoryGettersAndSettersWork() {
        Category category = new Category();
        category.setCategoryId(2L);
        category.setCategoryName("家電");

        Product product = new Product();
        List<Product> products = List.of(product);
        category.setProducts(products);

        assertEquals(2L, category.getCategoryId());
        assertEquals("家電", category.getCategoryName());
        assertSame(products, category.getProducts());
    }

    @Test
    void userGettersAndSettersWork() {
        User user = new User();
        user.setUserId("testuser");
        user.setPassword("secret");

        assertEquals("testuser", user.getUserId());
        assertEquals("secret", user.getPassword());
    }

    @Test
    void warehouseGettersAndSettersWork() {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId(3L);
        warehouse.setWarehouseName("東京倉庫");

        Area area = new Area();
        area.setAreaName("東日本");
        warehouse.setArea(area);

        Stock stock = new Stock();
        List<Stock> stocks = List.of(stock);
        warehouse.setStocks(stocks);

        assertEquals(3L, warehouse.getWarehouseId());
        assertEquals("東京倉庫", warehouse.getWarehouseName());
        assertSame(area, warehouse.getArea());
        assertSame(stocks, warehouse.getStocks());
    }

    @Test
    void productGettersAndSettersWork() {
        Product product = new Product();
        product.setProductId(4L);
        product.setProductName("サンプルテレビ");
        product.setPrice(50000);

        Category category = new Category();
        category.setCategoryName("家電");
        product.setCategory(category);

        assertEquals(4L, product.getProductId());
        assertEquals("サンプルテレビ", product.getProductName());
        assertEquals(50000, product.getPrice());
        assertSame(category, product.getCategory());
    }

    @Test
    void productImageGettersAndSettersWork() {
        ProductImage productImage = new ProductImage();
        productImage.setId(5L);

        Product product = new Product();
        product.setProductId(10L);
        productImage.setProduct(product);

        productImage.setImageNo(1);
        byte[] image = new byte[]{1, 2, 3};
        productImage.setImage(image);

        assertEquals(5L, productImage.getId());
        assertSame(product, productImage.getProduct());
        assertEquals(1, productImage.getImageNo());
        assertArrayEquals(image, productImage.getImage());
    }

    @Test
    void stockGettersAndSettersWork() {
        Stock stock = new Stock();
        stock.setId(6L);

        Product product = new Product();
        product.setProductId(11L);
        stock.setProduct(product);

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId(12L);
        stock.setWarehouse(warehouse);

        stock.setQuantity(10);

        assertEquals(6L, stock.getId());
        assertSame(product, stock.getProduct());
        assertSame(warehouse, stock.getWarehouse());
        assertEquals(10, stock.getQuantity());
    }
}
