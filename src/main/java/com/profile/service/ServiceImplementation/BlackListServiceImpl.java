package com.profile.service.ServiceImplementation;
import com.profile.models.entity.User;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.BlackListService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BlackListServiceImpl implements BlackListService {

    private final UserRepository userRepository;


    public BlackListServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUserToBlackList(User user) {

    }

    @Override
    public void deleteUserFromBlackList(User user) {

    }


}
