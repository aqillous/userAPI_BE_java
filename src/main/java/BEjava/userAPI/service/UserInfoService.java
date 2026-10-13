package BEjava.userAPI.service;

import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import BEjava.userAPI.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UserInfoService {

    private final UserRepository userRepository;


    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("UserInfoService yaradildi: {}", this.hashCode());
    }

    public UserInfoResponse getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public List<UserInfoResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserInfoResponse createUser(CreateUserRequest createUserRequest) {
        return userRepository.createUser(createUserRequest);
    }
}
