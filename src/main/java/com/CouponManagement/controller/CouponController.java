package com.CouponManagement.controller;

import com.CouponManagement.model.*;
import com.CouponManagement.impl.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.ok(createdCoupon);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Optional<Coupon> coupon = couponService.getCouponById(id);
        return coupon.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        if (updatedCoupon != null) {
            return ResponseEntity.ok(updatedCoupon);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/applicable-coupons")
    public ResponseEntity<List<Coupon>> getApplicableCoupons(@RequestBody Map<String, Object> cart) {
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) cart.get("cartItems");
        double cartTotal = (double) cart.get("cartTotal");
        List<Coupon> applicableCoupons = couponService.getApplicableCoupons(cartItems, cartTotal);
        return ResponseEntity.ok(applicableCoupons);
    }

    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<Map<String, Object>> applyCouponToCart(@PathVariable Long id, @RequestBody Map<String, Object> cart) {
        try {
            List<Map<String, Object>> cartItems = (List<Map<String, Object>>) cart.get("cartItems");
            double cartTotal = (double) cart.get("cartTotal");
            
            Map<String, Object> updatedCart = couponService.applyCouponToCart(id, cartItems, cartTotal);
            return ResponseEntity.ok(updatedCart);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
