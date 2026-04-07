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
    @Bean
    public CommandLineRunner loadData(UserRepository userRepository,
                                      CategoryRepository categoryRepository,
                                      ProductRepository productRepository,
                                      ProductImageRepository productImageRepository,
                                      StockRepository stockRepository,
                                      WarehouseRepository warehouseRepository,
                                      AreaRepository areaRepository) {
        return args -> {
            // ユーザーが未登録ならサンプルユーザーを作成
            if (userRepository.count() == 0) {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                User user = new User();
                user.setUserId("testuser"); // ユーザーID
                user.setPassword("{bcrypt}" + encoder.encode("testpass")); // パスワード（bcryptでハッシュ化）
                userRepository.save(user);
            }
            // 商品が未登録ならサンプル商品データ一式を作成
            if (productRepository.count() == 0) {
                Area area = new Area(); // 地域エンティティ
                area.setAreaName("東日本"); // 地域名
                areaRepository.save(area);
                // 倉庫エンティティ
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseName("東京倉庫"); // 倉庫名
                warehouse.setArea(area); // 地域と紐付け
                warehouseRepository.save(warehouse);
                // カテゴリエンティティ
                Category cat = new Category();
                cat.setCategoryName("家電"); // カテゴリ名
                categoryRepository.save(cat);
                // 商品エンティティ
                Product product = new Product();
                product.setProductName("サンプルテレビ"); // 商品名
                product.setPrice(50000); // 価格
                product.setCategory(cat); // カテゴリと紐付け
                productRepository.save(product);
                // 商品画像エンティティ（1x1透明PNGのBase64データをデコードして保存）
                ProductImage img = new ProductImage();
                img.setProduct(product); // 商品と紐付け
                img.setImageNo(1); // 画像番号
                img.setImage(java.util.Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/w8AAgMBAp9n8gAAAABJRU5ErkJggg=="));
                productImageRepository.save(img);
                // 在庫エンティティ
                Stock stock = new Stock();
                stock.setProduct(product); // 商品と紐付け
                stock.setWarehouse(warehouse); // 倉庫と紐付け
                stock.setQuantity(10); // 在庫数
                stockRepository.save(stock);

                // 2件目の商品エンティティ
                Product product2 = new Product();
                product2.setProductName("サンプル冷蔵庫"); // 商品名
                product2.setPrice(80000); // 価格
                product2.setCategory(cat); // カテゴリと紐付け
                productRepository.save(product2);

                // 2件目の商品画像
                ProductImage img2 = new ProductImage();
                img2.setProduct(product2);
                img2.setImageNo(1);
                img2.setImage(java.util.Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/w8AAgMBAp9n8gAAAABJRU5ErkJggg=="));
                productImageRepository.save(img2);

                // 2件目の在庫
                Stock stock2 = new Stock();
                stock2.setProduct(product2);
                stock2.setWarehouse(warehouse);
                stock2.setQuantity(5);
                stockRepository.save(stock2);
            }
        };
    }
}
