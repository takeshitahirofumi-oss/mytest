package com.example.ecsite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer imageNo;
    @Lob
    private byte[] image;

    public ProductImage() {}

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getImageNo() { return imageNo; }
    public void setImageNo(Integer imageNo) { this.imageNo = imageNo; }
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
}
