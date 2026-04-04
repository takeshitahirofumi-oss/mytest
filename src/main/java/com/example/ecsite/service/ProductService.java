package com.example.ecsite.service;

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
    private ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private StockRepository stockRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<ProductImage> findFirstImage(Long productId) {
        return productImageRepository.findAll().stream()
                .filter(img -> img.getProduct().getProductId().equals(productId))
                .findFirst();
    }

    public int getTotalStock(Long productId) {
        return stockRepository.findAll().stream()
                .filter(s -> s.getProduct().getProductId().equals(productId))
                .mapToInt(Stock::getQuantity)
                .sum();
    }
}
