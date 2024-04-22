package com.captain.springcloud.couponeservice.controllers;

import com.captain.springcloud.couponeservice.model.Coupon;
import com.captain.springcloud.couponeservice.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponapi")
public class CouponRestController {

    @Autowired
    CouponRepository couponRepository;

    @PostMapping("/coupons")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    public Coupon getCoupon(String code) {
        return couponRepository.findByCode(code);
    }

}
