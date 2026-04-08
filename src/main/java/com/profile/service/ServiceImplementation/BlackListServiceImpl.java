package com.profile.service.ServiceImplementation;
import com.profile.models.entity.BlackList;
import com.profile.models.entity.User;
import com.profile.repository.BlackListRepository;
import com.profile.repository.UserRepository;
import com.profile.service.serviceAnotation.BlackListService;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class BlackListServiceImpl implements BlackListService {

    private final UserRepository userRepository;
    private final BlackListRepository blackListRepository;
    private final ModelMapper modelMapper;



    public BlackListServiceImpl(UserRepository userRepository, BlackListRepository blackListRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.blackListRepository = blackListRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addUserToBlackList(User user) {
       BlackList foundUser =  this.blackListRepository.findByUserUsername(user.getUsername()).orElse(null);
       if (foundUser==null){
           this.blackListRepository.save(modelMapper.map(user, BlackList.class));
       }



    }

    @Override
    public void deleteUserFromBlackList(User user) {
        this.blackListRepository.findByUserUsername(user.getUsername()).ifPresent(this.blackListRepository::delete);
    }


}
