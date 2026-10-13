package BEjava.userAPI.repository;

import BEjava.userAPI.component.RequestContext;
import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class MockUserRepository implements UserRepository {

    private final RequestContext requestContext;

    public MockUserRepository(RequestContext requestContext) {
        this.requestContext = requestContext;
        log.info("MockUserRepository yaradildi: {}", this.hashCode());
    }

    @Override
    public UserInfoResponse getUserById(Long id) {
        log.info("[MockRepository] requestId={}", requestContext.getRequestId());
        return mockUser(id);
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return List.of(mockUser(1L), mockUser(2L));
    }

    @Override
    public UserInfoResponse createUser(CreateUserRequest request) {
        return mockUser(100L);
    }

    private UserInfoResponse mockUser(Long id) {
        return new UserInfoResponse(id, "Mock User", "mock" + id + "@example.com", true, LocalDateTime.now());
    }
}
