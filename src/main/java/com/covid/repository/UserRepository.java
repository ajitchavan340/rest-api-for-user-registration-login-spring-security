package com.covid.repository;

import com.covid.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByusername(String username);
//     User findByEmail(String email);
}
