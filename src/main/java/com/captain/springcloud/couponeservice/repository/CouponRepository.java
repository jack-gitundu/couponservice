package com.captain.springcloud.couponeservice.repository;

import com.captain.springcloud.couponeservice.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
