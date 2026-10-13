package BEjava.userAPI.component;

import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Getter
@Setter
@Scope("prototype")
@Component
public class UserAuditLog {

    private final String auditId = UUID.randomUUID().toString();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Setter
    private String action;

    public UserAuditLog() {
        log.info("UserAuditLog yaradildi: {}", auditId);
    }

    @PreDestroy
    public void destroy() {
        log.info("UserAuditLog destroy olunur: {}", auditId);
    }

}
