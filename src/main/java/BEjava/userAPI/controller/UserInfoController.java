package BEjava.userAPI.controller;

import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import BEjava.userAPI.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/v1/user-info")
public class UserInfoController {

    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
        log.info("UserInfoController yaradildi: {}", this.hashCode());
    }

    @GetMapping("/{id}")
    public UserInfoResponse getUserById(@PathVariable Long id) {
        return userInfoService.getUserById(id);
    }

    @GetMapping("/all")
    public List<UserInfoResponse> getAllUsers() {
        return userInfoService.getAllUsers();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userInfoService.createUser(createUserRequest);
    }
}
