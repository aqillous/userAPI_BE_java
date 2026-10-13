package BEjava.userAPI.service;

import BEjava.userAPI.component.RecentlyViewedUsers;
import BEjava.userAPI.component.RequestContext;
import BEjava.userAPI.component.UserAuditLog;
import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import BEjava.userAPI.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final ObjectProvider<UserAuditLog> userAuditLogProvider;
    private final RequestContext requestContext;
    private final RecentlyViewedUsers recentlyViewedUsers;

    public UserInfoService(UserRepository userRepository,
                           ObjectProvider<UserAuditLog> userAuditLogProvider,
                           RequestContext requestContext,
                           RecentlyViewedUsers recentlyViewedUsers) {
        this.userRepository = userRepository;
        this.userAuditLogProvider = userAuditLogProvider;
        this.requestContext = requestContext;
        this.recentlyViewedUsers = recentlyViewedUsers;
        log.info("UserInfoService yaradildi: {}", this.hashCode());
    }

    public UserInfoResponse getUserById(Long id) {
        log.info("[Service] requestId={}", requestContext.getRequestId());
        UserInfoResponse user = userRepository.getUserById(id);
        recentlyViewedUsers.addUser(id);
        return user;
    }

    public List<UserInfoResponse> getRecentUsers() {
        return recentlyViewedUsers.getUsers().stream()
                .map(userRepository::getUserById)
                .toList();
    }

    public List<UserInfoResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserInfoResponse createUser(CreateUserRequest request) {
        UserAuditLog audit = userAuditLogProvider.getObject();
        audit.setAction("CREATE_USER");
        log.info("[Audit] auditId={}, action={}, createdAt={}",
                audit.getAuditId(), audit.getAction(), audit.getCreatedAt());
        return userRepository.createUser(request);
    }
}
