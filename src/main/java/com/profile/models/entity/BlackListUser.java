package com.profile.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "blacklist")
public class BlackListUser extends BaseEntity {

    @OneToOne
    private User bannedUser;
}


