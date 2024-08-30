package com.CouponManagement.model;

import lombok.Data;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;  // cart-wise, product-wise, bxgy

    @Column(nullable = false)
    private double discountValue;  // Percentage or fixed amount

    private double threshold;  // For cart-wise coupons

    @ElementCollection
    @CollectionTable(name = "applicable_products", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "product_id")
    private List<String> applicableProducts;  // For product-wise and BxGy coupons

    private int buyQuantity;  // For BxGy coupons

    private int getQuantity;  // For BxGy coupons

    @ElementCollection
    @CollectionTable(name = "free_products", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "product_id")
    private List<String> freeProducts;  // For BxGy coupons
}

