package com.mayur.portfolio.task.manager.repository;

import com.mayur.portfolio.task.manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}