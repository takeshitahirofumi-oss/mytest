package com.example.ecsite.config;

import com.example.ecsite.entity.*;
import com.example.ecsite.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.*;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
                                      CategoryRepository categoryRepository,
                                      ProductRepository productRepository,
                                      ProductImageRepository productImageRepository,
                                      StockRepository stockRepository,
                                      WarehouseRepository warehouseRepository,
                                      AreaRepository areaRepository) {
        return args -> {
            // ユーザー
            if (userRepository.count() == 0) {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                User user = new User();
                user.setUserId("testuser");
                user.setPassword("{bcrypt}" + encoder.encode("testpass"));
                userRepository.save(user);
            }
            // サンプル商品関連データ
            if (productRepository.count() == 0) {
                Area area = new Area();
                area.setAreaName("東日本");
                areaRepository.save(area);
                // 倉庫
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseName("東京倉庫");
                warehouse.setArea(area);
                warehouseRepository.save(warehouse);
                // カテゴリ
                Category cat = new Category();
                cat.setCategoryName("家電");
                categoryRepository.save(cat);
                // 商品
                Product product = new Product();
                product.setProductName("サンプルテレビ");
                product.setPrice(50000);
                product.setCategory(cat);
                productRepository.save(product);
                // 商品画像（プレースホルダーPNG）
                ProductImage img = new ProductImage();
                img.setProduct(product);
                img.setImageNo(1);
                // 1x1透明PNG (Base64: iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/w8AAgMBAp9n8gAAAABJRU5ErkJggg==)
                img.setImage(java.util.Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/w8AAgMBAp9n8gAAAABJRU5ErkJggg=="));
                productImageRepository.save(img);
                // 在庫
                Stock stock = new Stock();
                stock.setProduct(product);
                stock.setWarehouse(warehouse);
                stock.setQuantity(10);
                stockRepository.save(stock);
            }
        };
    }
}
