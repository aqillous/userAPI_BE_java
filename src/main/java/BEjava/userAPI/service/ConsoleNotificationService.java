package BEjava.userAPI.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsoleNotificationService implements NotificationService {
    @Override
    public void sendWelcome(String email) {
        log.info("Xos geldin, {}!", email);
    }
}
