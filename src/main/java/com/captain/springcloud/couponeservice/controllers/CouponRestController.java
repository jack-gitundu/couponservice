package com.captain.springcloud.couponeservice.controllers;

import com.captain.springcloud.couponeservice.model.Coupon;
import com.captain.springcloud.couponeservice.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

    @Autowired
    CouponRepository couponRepository;

    @PostMapping("/coupons")
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon create(@RequestBody Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Coupon getCoupon(@PathVariable String code) {
        return couponRepository.findByCode(code);
    }

}
