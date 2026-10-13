package BEjava.userAPI.repository;


import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;

import java.util.List;

public interface UserRepository {

    UserInfoResponse getUserById(Long id);

    List<UserInfoResponse> getAllUsers();

    UserInfoResponse createUser(CreateUserRequest request);
}
