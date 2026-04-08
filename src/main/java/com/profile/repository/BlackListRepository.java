package com.profile.repository;

import com.profile.models.entity.BlackListUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackListRepository extends JpaRepository<BlackListUser, Long> {

    Optional <BlackListUser> findByBannedUser_Username(String username);
}
