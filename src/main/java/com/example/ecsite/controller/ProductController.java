package com.example.ecsite.controller;

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
    private ProductService productService;

    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("productService", productService);
        return "product_list";
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
