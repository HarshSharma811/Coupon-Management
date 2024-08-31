package com.CouponManagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CouponManagement.model.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
