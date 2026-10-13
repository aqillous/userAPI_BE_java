package BEjava.userAPI.service;

import BEjava.userAPI.component.RecentlyViewedUsers;
import BEjava.userAPI.component.RequestContext;
import BEjava.userAPI.component.UserAuditLog;
import BEjava.userAPI.component.UserStatistics;
import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import BEjava.userAPI.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserInfoServiceTest {
    private UserRepository repository;
    private RecentlyViewedUsers recentlyViewedUsers;
    private UserInfoService service;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        repository = Mockito.mock(UserRepository.class);
        recentlyViewedUsers = Mockito.mock(RecentlyViewedUsers.class);
        RequestContext requestContext = Mockito.mock(RequestContext.class);
        ObjectProvider<UserAuditLog> auditProvider = Mockito.mock(ObjectProvider.class);
        when(auditProvider.getObject()).thenReturn(new UserAuditLog());

        service = new UserInfoService(repository, List.of(repository), auditProvider,
                requestContext, recentlyViewedUsers, new UserStatistics());
    }

    @Test
    void getUserById_returnsUser_andTracksItAsRecentlyViewed() {
        UserInfoResponse expected = new UserInfoResponse(1L, "Test", "t@example.com", true, LocalDateTime.now());
        when(repository.getUserById(1L)).thenReturn(expected);

        UserInfoResponse actual = service.getUserById(1L);

        assertEquals(expected, actual);
        verify(recentlyViewedUsers).addUser(1L);
    }

    @Test
    void createUser_sendsWelcomeNotification() {
        NotificationService notification = Mockito.mock(NotificationService.class);
        service.setNotificationService(notification);
        UserInfoResponse created = new UserInfoResponse(4L, "Test", "t@example.com", true, LocalDateTime.now());
        when(repository.createUser(any())).thenReturn(created);

        service.createUser(new CreateUserRequest("Test", "t@example.com"));

        verify(notification).sendWelcome("t@example.com");
    }
}