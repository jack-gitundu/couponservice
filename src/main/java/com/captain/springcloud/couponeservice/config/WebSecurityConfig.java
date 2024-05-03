package com.captain.springcloud.couponeservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class WebSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}", "/showGetCoupon", "/getCoupon")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "/createResponse")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/couponapi/coupons", "saveCoupon").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/", "/login", "/showReg", "/registerUser").permitAll());

        http.logout().logoutSuccessUrl("/");
//        http.csrf(csrf -> csrf.disable());
        http.csrf(csrfCustomizer -> {
            csrfCustomizer.ignoringRequestMatchers("/couponapi/coupons/**");
            RequestMatcher requestMatcher = new RegexRequestMatcher("/couponapi/coupons/{code:^[A-Z]*$}", "POST");
            requestMatcher = new MvcRequestMatcher(new HandlerMappingIntrospector(), "/getCoupon");
            csrfCustomizer.ignoringRequestMatchers(requestMatcher);
        });
        http.securityContext(context -> context.requireExplicitSave(true));
        return http.build();
    }
}
