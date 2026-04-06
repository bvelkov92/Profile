package com.profile.service.serviceAnotation;


import com.profile.models.entity.User;

public interface BlackListService {

    void addUserToBlackList(User user);

    void deleteUserFromBlackList(User user);
}
