package com.profile.repository;

import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional <User> findByUsername(String username);
   List<User> findAllByRole(RolesEnum admin);
}
