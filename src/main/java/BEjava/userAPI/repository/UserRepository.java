package BEjava.userAPI.repository;

import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class UserRepository {

    public UserInfoResponse getUserById(Long id){
        return new UserInfoResponse(
                id,
                "test_istifadeci",
                "user" + id + "@example.com",
                true,
                LocalDateTime.now()
        );
    }

    public List<UserInfoResponse> getAllUsers() {
        return List.of(getUserById(1L), getUserById(2L), getUserById(3L));
    }

    public UserInfoResponse createUser(CreateUserRequest createUserRequest) {
        UserInfoResponse user = new UserInfoResponse();
        user.setId(ThreadLocalRandom.current().nextLong(1, 1000));
        user.setFullName(createUserRequest.getFullName());
        user.setEmail(createUserRequest.getEmail());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }


}
