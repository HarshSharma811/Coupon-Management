package com.CouponManagement.impl;

import com.CouponManagement.model.Coupon;
import com.CouponManagement.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }

    public Coupon updateCoupon(Long id, Coupon coupon) {
        Optional<Coupon> existingCoupon = couponRepository.findById(id);
        if (existingCoupon.isPresent()) {
            coupon.setId(id);
            return couponRepository.save(coupon);
        }
        return null;
    }

    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }

    public List<Coupon> getApplicableCoupons(List<Map<String, Object>> cartItems, double cartTotal) {
        List<Coupon> allCoupons = getAllCoupons();
        List<Coupon> applicableCoupons = new ArrayList<>();

        for (Coupon coupon : allCoupons) {
            if (isCouponApplicable(coupon, cartItems, cartTotal)) {
                applicableCoupons.add(coupon);
            }
        }

        return applicableCoupons;
    }

    public Map<String, Object> applyCouponToCart(Long couponId, List<Map<String, Object>> cartItems, double cartTotal) {
        Optional<Coupon> optionalCoupon = getCouponById(couponId);
        if (!optionalCoupon.isPresent()) {
            throw new IllegalArgumentException("Coupon not found");
        }

        Coupon coupon = optionalCoupon.get();
        if (!isCouponApplicable(coupon, cartItems, cartTotal)) {
            throw new IllegalArgumentException("Coupon conditions not met");
        }

        switch (coupon.getType()) {
            case "cart-wise":
                return applyCartWiseCoupon(coupon, cartItems, cartTotal);
            case "product-wise":
                return applyProductWiseCoupon(coupon, cartItems, cartTotal);
            case "bxgy":
                return applyBxGyCoupon(coupon, cartItems);
            default:
                throw new IllegalArgumentException("Unknown coupon type");
        }
    }

    private boolean isCouponApplicable(Coupon coupon, List<Map<String, Object>> cartItems, double cartTotal) {
        switch (coupon.getType()) {
            case "cart-wise":
                return cartTotal >= coupon.getThreshold();
            case "product-wise":
                return cartItems.stream()
                        .anyMatch(item -> coupon.getApplicableProducts().contains(item.get("productId")));
            case "bxgy":
                long applicableProductCount = cartItems.stream()
                        .filter(item -> coupon.getApplicableProducts().contains(item.get("productId")))
                        .count();
                return applicableProductCount >= coupon.getBuyQuantity();
            default:
                return false;
        }
    }

    private Map<String, Object> applyCartWiseCoupon(Coupon coupon, List<Map<String, Object>> cartItems, double cartTotal) {
        double discount = cartTotal * (coupon.getDiscountValue() / 100.0);
        cartTotal -= discount;
        return Map.of("cartItems", cartItems, "cartTotal", cartTotal, "discountApplied", discount);
    }

    private Map<String, Object> applyProductWiseCoupon(Coupon coupon, List<Map<String, Object>> cartItems, double cartTotal) {
        double discount = 0.0;

        for (Map<String, Object> item : cartItems) {
            if (coupon.getApplicableProducts().contains(item.get("productId"))) {
                double itemPrice = (double) item.get("price");
                double itemDiscount = itemPrice * (coupon.getDiscountValue() / 100.0);
                item.put("price", itemPrice - itemDiscount);
                discount += itemDiscount;
            }
        }

        cartTotal -= discount;
        return Map.of("cartItems", cartItems, "cartTotal", cartTotal, "discountApplied", discount);
    }

    private Map<String, Object> applyBxGyCoupon(Coupon coupon, List<Map<String, Object>> cartItems) {
        List<Map<String, Object>> applicableItems = cartItems.stream()
                .filter(item -> coupon.getApplicableProducts().contains(item.get("productId")))
                .collect(Collectors.toList());

        if (applicableItems.size() >= coupon.getBuyQuantity()) {
            int freeItemCount = applicableItems.size() / coupon.getBuyQuantity() * coupon.getGetQuantity();
            int freeItemsApplied = 0;

            for (Map<String, Object> item : cartItems) {
                if (freeItemsApplied >= freeItemCount) break;

                if (coupon.getFreeProducts().contains(item.get("productId"))) {
                    item.put("price", 0.0);
                    freeItemsApplied++;
                }
            }
        }

        double updatedCartTotal = cartItems.stream()
                .mapToDouble(item -> (double) item.get("price"))
                .sum();

        return Map.of("cartItems", cartItems, "cartTotal", updatedCartTotal);
    }
}
