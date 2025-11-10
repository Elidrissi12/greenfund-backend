package com.greenfund.greenfund_backend.repository;

import com.greenfund.greenfund_backend.model.entity.User;
import com.greenfund.greenfund_backend.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByActive(Boolean active);
}

