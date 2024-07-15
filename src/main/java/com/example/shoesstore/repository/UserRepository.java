package com.example.shoesstore.repository;

import com.example.shoesstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByFacebookAccount(String fbAccount);

    Optional<User> findByGoogleAccount(String googleAccount);

}
