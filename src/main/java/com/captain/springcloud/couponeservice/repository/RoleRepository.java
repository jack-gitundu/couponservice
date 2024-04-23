package com.captain.springcloud.couponeservice.repository;

import com.captain.springcloud.couponeservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
