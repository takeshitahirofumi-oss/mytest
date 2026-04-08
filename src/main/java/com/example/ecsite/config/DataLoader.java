package com.example.ecsite.config;

// サンプルデータをDBに投入するための設定クラス
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
    private static final String PLACEHOLDER_PNG_BASE64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/w8AAgMBAp9n8gAAAABJRU5ErkJggg==";

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
                                      CategoryRepository categoryRepository,
                                      ProductRepository productRepository,
                                      ProductImageRepository productImageRepository,
                                      StockRepository stockRepository,
                                      WarehouseRepository warehouseRepository,
                                      AreaRepository areaRepository) {
        return args -> {
            // サンプルユーザーが未登録なら作成
            if (userRepository.findByUserId("testuser") == null) {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                User user = new User();
                user.setUserId("testuser"); // ユーザーID
                user.setPassword("{bcrypt}" + encoder.encode("testpass")); // パスワード（bcryptでハッシュ化）
                userRepository.save(user);
            }

            Area area = areaRepository.findAll().stream()
                    .filter(a -> "東日本".equals(a.getAreaName()))
                    .findFirst()
                    .orElseGet(() -> {
                        Area a = new Area();
                        a.setAreaName("東日本");
                        return areaRepository.save(a);
                    });

            Warehouse warehouse = warehouseRepository.findAll().stream()
                    .filter(w -> "東京倉庫".equals(w.getWarehouseName()))
                    .findFirst()
                    .orElseGet(() -> {
                        Warehouse w = new Warehouse();
                        w.setWarehouseName("東京倉庫");
                        w.setArea(area);
                        return warehouseRepository.save(w);
                    });

            Category category = categoryRepository.findAll().stream()
                    .filter(c -> "家電".equals(c.getCategoryName()))
                    .findFirst()
                    .orElseGet(() -> {
                        Category c = new Category();
                        c.setCategoryName("家電");
                        return categoryRepository.save(c);
                    });

            ensureSampleProduct("サンプルテレビ", 50000, 10, category, warehouse,
                    productRepository, productImageRepository, stockRepository);
            ensureSampleProduct("サンプル冷蔵庫", 80000, 5, category, warehouse,
                    productRepository, productImageRepository, stockRepository);
        };
    }

    private void ensureSampleProduct(String productName,
                                     int price,
                                     int quantity,
                                     Category category,
                                     Warehouse warehouse,
                                     ProductRepository productRepository,
                                     ProductImageRepository productImageRepository,
                                     StockRepository stockRepository) {
        Product product = productRepository.findAll().stream()
                .filter(p -> productName.equals(p.getProductName()))
                .findFirst()
                .orElseGet(() -> {
                    Product p = new Product();
                    p.setProductName(productName);
                    p.setPrice(price);
                    p.setCategory(category);
                    return productRepository.save(p);
                });

        boolean hasImage = productImageRepository.findAll().stream()
                .anyMatch(img -> img.getProduct() != null
                        && productName.equals(img.getProduct().getProductName())
                        && Integer.valueOf(1).equals(img.getImageNo()));

        if (!hasImage) {
            ProductImage image = new ProductImage();
            image.setProduct(product);
            image.setImageNo(1);
            image.setImage(java.util.Base64.getDecoder().decode(PLACEHOLDER_PNG_BASE64));
            productImageRepository.save(image);
        }

        boolean hasStock = stockRepository.findAll().stream()
                .anyMatch(s -> s.getProduct() != null
                        && s.getWarehouse() != null
                        && productName.equals(s.getProduct().getProductName())
                        && "東京倉庫".equals(s.getWarehouse().getWarehouseName()));

        if (!hasStock) {
            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setWarehouse(warehouse);
            stock.setQuantity(quantity);
            stockRepository.save(stock);
        }
    }
}
