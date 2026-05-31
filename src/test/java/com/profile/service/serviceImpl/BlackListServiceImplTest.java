package com.profile.service.serviceImpl;

import com.profile.models.entity.BlackListUser;
import com.profile.models.entity.User;
import com.profile.models.enums.RolesEnum;
import com.profile.repository.BlackListRepository;
import com.profile.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlackListServiceImplTest {

    @Mock
    private BlackListServiceImpl mockBlackListService;

    @Mock
    private BlackListRepository mockBlackListRepository;
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ModelMapper mockModelMapper;

    private User mockedUser;

    @BeforeEach
    void setUp(){
        mockBlackListService = new BlackListServiceImpl( mockUserRepository, mockBlackListRepository,mockModelMapper);

        mockedUser = new User();
        mockedUser.setUsername("username");
        mockedUser.setBanned(false);
        mockedUser.setRole(RolesEnum.USER);

    }

    @Test
    void addUserToBlackList() {
        BlackListUser mockedBlackListSUser = new BlackListUser();
        String bannedUser = "username";

        when(mockBlackListRepository.findByBannedUser_Username(bannedUser)).thenReturn(Optional.empty());
        when(mockModelMapper.map(mockedUser, BlackListUser.class)).thenReturn(mockedBlackListSUser);

        mockBlackListService.addUserToBlackList(mockedUser);

        verify(mockBlackListRepository).save(mockedBlackListSUser);
    }

    @Test
    void deleteUserFromBlackList() {

        BlackListUser blackListUser = new BlackListUser();
        String findUsername = "username";
        when(mockBlackListRepository.findByBannedUser_Username(findUsername)).thenReturn(Optional.of(blackListUser));

        mockBlackListService.deleteUserFromBlackList(mockedUser);

        verify(mockBlackListRepository).delete(blackListUser);
    }

}