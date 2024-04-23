package com.captain.springcloud.couponeservice.repository;

import com.captain.springcloud.couponeservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
