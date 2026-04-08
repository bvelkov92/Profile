package com.profile.repository;

import com.profile.models.entity.BlackList;
import com.profile.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Long> {

    Optional <BlackList> findByUserUsername(String username);
}
