package com.example.ecsite.service;

// 商品情報の取得・操作を担当するサービスクラス
import com.example.ecsite.entity.Product;
import com.example.ecsite.entity.ProductImage;
import com.example.ecsite.entity.Stock;
import com.example.ecsite.repository.ProductImageRepository;
import com.example.ecsite.repository.ProductRepository;
import com.example.ecsite.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository; // 商品情報のDBアクセスを担当
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private StockRepository stockRepository;

    // 全商品を取得
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // 商品IDで商品を取得
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductImage> findFirstImage(Long productId) {
        return productImageRepository.findAll().stream()
                .filter(img -> img.getProduct().getProductId().equals(productId))
                .findFirst();
    }

    public int getTotalStock(Long productId) {
        return stockRepository.findAll().stream()
                .filter(s -> s.getProduct().getProductId().equals(productId))
                .mapToInt(s -> s.getQuantity() != null ? s.getQuantity() : 0)
                .sum();
    }
}
