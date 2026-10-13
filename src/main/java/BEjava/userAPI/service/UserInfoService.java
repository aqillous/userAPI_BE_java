package BEjava.userAPI.service;

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

    public UserInfoService(UserRepository userRepository,
                           ObjectProvider<UserAuditLog> userAuditLogProvider) {
        this.userRepository = userRepository;
        this.userAuditLogProvider = userAuditLogProvider;
        log.info("UserInfoService yaradildi: {}", this.hashCode());
    }

    public UserInfoResponse getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    public List<UserInfoResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserInfoResponse createUser(CreateUserRequest request) {
        UserAuditLog audit = userAuditLogProvider.getObject();   // NEW object each call
        audit.setAction("CREATE_USER");
        log.info("[Audit] auditId={}, action={}, createdAt={}",
                audit.getAuditId(), audit.getAction(), audit.getCreatedAt());
        return userRepository.createUser(request);
    }
}
