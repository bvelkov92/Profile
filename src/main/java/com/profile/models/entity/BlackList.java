package com.profile.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "blacklist")
public class BlackList {

    @OneToOne
    private User bannedUser;
}


