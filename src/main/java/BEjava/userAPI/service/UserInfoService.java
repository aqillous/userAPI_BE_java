package BEjava.userAPI.service;

import BEjava.userAPI.component.RecentlyViewedUsers;
import BEjava.userAPI.component.RequestContext;
import BEjava.userAPI.component.UserAuditLog;
import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import BEjava.userAPI.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final ObjectProvider<UserAuditLog> userAuditLogProvider;
    private final RequestContext requestContext;
    private final RecentlyViewedUsers recentlyViewedUsers;

    private NotificationService notificationService;   // optional

    public UserInfoService(@Qualifier("mockUserRepository") UserRepository userRepository,
                           List<UserRepository> allRepositories,           // task 33
                           ObjectProvider<UserAuditLog> userAuditLogProvider,
                           RequestContext requestContext,
                           RecentlyViewedUsers recentlyViewedUsers) {
        this.userRepository = userRepository;
        this.userAuditLogProvider = userAuditLogProvider;
        this.requestContext = requestContext;
        this.recentlyViewedUsers = recentlyViewedUsers;
        log.info("UserInfoService yaradildi: {}", this.hashCode());
        log.info("List<UserRepository> element sayi: {} -> {}", allRepositories.size(),
                allRepositories.stream().map(r -> r.getClass().getSimpleName()).toList());
    }

    @Autowired(required = false)
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
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

        UserInfoResponse created = userRepository.createUser(request);

        if (notificationService != null) {
            notificationService.sendWelcome(created.getEmail());
        }
        return created;
    }
}
