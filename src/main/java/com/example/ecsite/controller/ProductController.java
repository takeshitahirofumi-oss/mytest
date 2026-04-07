package com.example.ecsite.controller;

// 商品一覧・商品詳細の画面表示や操作を担当するコントローラー
import com.example.ecsite.entity.Product;
import com.example.ecsite.entity.ProductImage;
import com.example.ecsite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService; // 商品情報の取得・操作を行うサービス

    // 商品一覧ページの表示
    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products); // 商品一覧を取得して画面に渡す
        model.addAttribute("productService", productService);
        return "product_list"; // templates/product_list.htmlを表示
    }

    // 商品詳細ページの表示
    @GetMapping("/products/{id}")
    public String showProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id)); // 商品詳細を取得して画面に渡す
        return "product_detail"; // templates/product_detail.htmlを表示
    }

    @GetMapping("/product/image/{productId}")
    @ResponseBody
    public String productImage(@PathVariable Long productId) {
        Optional<ProductImage> imgOpt = productService.findFirstImage(productId);
        if (imgOpt.isPresent()) {
            byte[] img = imgOpt.get().getImage();
            String base64 = Base64.getEncoder().encodeToString(img);
            return "data:image/jpeg;base64," + base64;
        }
        return "";
    }
}
