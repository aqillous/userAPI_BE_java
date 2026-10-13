package BEjava.userAPI.controller;

import BEjava.userAPI.component.RequestContext;
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
    private final RequestContext requestContext;

    public UserInfoController(UserInfoService userInfoService, RequestContext requestContext) {
        this.userInfoService = userInfoService;
        this.requestContext = requestContext;
        log.info("UserInfoController yaradildi: {}", this.hashCode());
    }

    @GetMapping("/{id}")
    public UserInfoResponse getUserById(@PathVariable Long id) {
        log.info("[Controller] requestId={}", requestContext.getRequestId());
        log.info("[Controller] requestContext class = {}", requestContext.getClass().getName()); // task 19

        UserInfoResponse response = userInfoService.getUserById(id);

        long elapsed = System.currentTimeMillis() - requestContext.getStartTime();               // task 17
        log.info("[Controller] requestId={}, sorgu {} ms cekdi", requestContext.getRequestId(), elapsed);
        return response;
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

    @GetMapping("/recent")
    public List<UserInfoResponse> getRecentUsers() {
        return userInfoService.getRecentUsers();
    }
}
