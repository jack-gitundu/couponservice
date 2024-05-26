package com.captain.springcloud.couponeservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CouponeserviceApplicationTests {

    @Autowired
    MockMvc mvc;

    @Test
    public void testGetCouponWithoutAuth_Forbidden() throws Exception {
        mvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testGetCouponWithAuth_Success() throws Exception {
        mvc.perform(get("/couponapi/coupons/SUPERSALE")).andExpect(content().string("{\"id\":1,\"code\":\"SUPERSALE\",\"discount\":10.000,\"expDate\":\"12/12/2025\"}"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testCreateCoupon_WithoutCSRF_Forbidden() throws Exception {
        mvc.perform(post("/couponapi/coupons")
                .content("{\"code\":\"SUPERSALECSRF\",\"discount\":80.000,\"expDate\":\"12/12/2025\"}")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testCreateCoupon_WithCSRF_Forbidden() throws Exception {
        mvc.perform(post("/couponapi/coupons")
                .content("{\"code\":\"SUPERSALECSRF\",\"discount\":80.000,\"expDate\":\"12/12/2025\"}")
                .contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testCreateCoupon_NonAdminUser_Forbidden() throws Exception {
        mvc.perform(post("/couponapi/coupons")
                .content("{\"code\":\"SUPERSALECSRF\",\"discount\":80.000,\"expDate\":\"12/12/2025\"}")
                .contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader())).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void textCORS() throws Exception {
        mvc.perform(options("/couponapi/coupons")
                        .header("Access-Control-Allow-Methods", "POST")
                        .header("Origin", "http://localhost:9091")).andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Methods", "POST"));
    }

}
