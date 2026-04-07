package com.example.ecsite.controller;

import com.example.ecsite.entity.Product;
import com.example.ecsite.entity.ProductImage;
import com.example.ecsite.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @Test
    void productListSetsModelAndReturnsViewName() {
        List<Product> products = List.of(new Product());
        when(productService.findAll()).thenReturn(products);

        String view = productController.productList(model);

        assertEquals("product_list", view);
        verify(model).addAttribute("products", products);
        verify(model).addAttribute("productService", productService);
    }

    @Test
    void showProductSetsModelAndReturnsViewName() {
        Product p = new Product();
        p.setProductId(5L);
        Optional<Product> productOpt = Optional.of(p);
        when(productService.findById(5L)).thenReturn(productOpt);

        String view = productController.showProduct(5L, model);

        assertEquals("product_detail", view);
        verify(model).addAttribute("product", productOpt);
    }

    @Test
    void productImageReturnsBase64DataUriWhenImageExists() {
        byte[] bytes = new byte[]{1, 2, 3};
        ProductImage image = new ProductImage();
        image.setImage(bytes);
        when(productService.findFirstImage(1L)).thenReturn(Optional.of(image));

        String result = productController.productImage(1L);

        String expected = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bytes);
        assertEquals(expected, result);
    }

    @Test
    void productImageReturnsEmptyWhenImageDoesNotExist() {
        when(productService.findFirstImage(999L)).thenReturn(Optional.empty());

        String result = productController.productImage(999L);

        assertTrue(result.isEmpty());
    }
}
