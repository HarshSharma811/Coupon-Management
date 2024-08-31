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
import jakarta.persistence.JoinColumn;
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

    private Double threshold;  // For cart-wise coupons, changed to `Double` for nullability

    @ElementCollection
    @CollectionTable(name = "applicable_products", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "product_id")
    private List<String> applicableProducts;  // For product-wise and BxGy coupons

    private Integer buyQuantity;  // For BxGy coupons, changed to `Integer` for nullability

    private Integer getQuantity;  // For BxGy coupons, changed to `Integer` for nullability

    @ElementCollection
    @CollectionTable(name = "free_products", joinColumns = @JoinColumn(name = "coupon_id"))
    @Column(name = "product_id")
    private List<String> freeProducts;  // For BxGy coupons

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public List<String> getApplicableProducts() {
		return applicableProducts;
	}

	public void setApplicableProducts(List<String> applicableProducts) {
		this.applicableProducts = applicableProducts;
	}

	public Integer getBuyQuantity() {
		return buyQuantity;
	}

	public void setBuyQuantity(Integer buyQuantity) {
		this.buyQuantity = buyQuantity;
	}

	public Integer getGetQuantity() {
		return getQuantity;
	}

	public void setGetQuantity(Integer getQuantity) {
		this.getQuantity = getQuantity;
	}

	public List<String> getFreeProducts() {
		return freeProducts;
	}

	public void setFreeProducts(List<String> freeProducts) {
		this.freeProducts = freeProducts;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", type=" + type + ", discountValue=" + discountValue + ", threshold=" + threshold
				+ ", applicableProducts=" + applicableProducts + ", buyQuantity=" + buyQuantity + ", getQuantity="
				+ getQuantity + ", freeProducts=" + freeProducts + "]";
	}

	public Coupon(Long id, String type, double discountValue, Double threshold, List<String> applicableProducts,
			Integer buyQuantity, Integer getQuantity, List<String> freeProducts) {
		super();
		this.id = id;
		this.type = type;
		this.discountValue = discountValue;
		this.threshold = threshold;
		this.applicableProducts = applicableProducts;
		this.buyQuantity = buyQuantity;
		this.getQuantity = getQuantity;
		this.freeProducts = freeProducts;
	}

	public Coupon() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
