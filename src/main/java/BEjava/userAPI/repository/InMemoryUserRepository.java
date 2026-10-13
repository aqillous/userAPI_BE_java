package BEjava.userAPI.repository;

import BEjava.userAPI.component.RequestContext;
import BEjava.userAPI.dto.request.CreateUserRequest;
import BEjava.userAPI.dto.response.UserInfoResponse;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
@ConditionalOnProperty(name = "app.repository-type", havingValue = "memory", matchIfMissing = true)
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long , UserInfoResponse> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();
    private final RequestContext requestContext;

    public InMemoryUserRepository(RequestContext requestContext) {
        this.requestContext = requestContext;
        log.info("UserRepository yaradildi: {}", this.hashCode());
    }

    @PostConstruct
    public void init(){
        createUser(new CreateUserRequest("Ali Aliyev", "ali@example.com"));
        createUser(new CreateUserRequest("Leyla Mammadova", "leyla@example.com"));
        createUser(new CreateUserRequest("Rashad Hasanov", "rashad@example.com"));
        log.info("UserRepository hazirdir, {} user yuklendi", storage.size());
    }
    @PreDestroy
    public void destroy() {
        log.info("UserRepository baglanir, storage temizlenir ({} user)", storage.size());
        storage.clear();
    }

    @Override
    public UserInfoResponse getUserById(Long id) {
        UserInfoResponse user = storage.get(id);
        if (user == null) {
            throw new NoSuchElementException("User tapilmadi: id=" + id);
        }
        return user;
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return storage.values().stream()
                .sorted(Comparator.comparing(UserInfoResponse::getId))
                .toList();
    }

    @Override
    public UserInfoResponse createUser(CreateUserRequest request) {
        UserInfoResponse user = new UserInfoResponse();
        user.setId(idGenerator.incrementAndGet());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        storage.put(user.getId(), user);
        return user;
    }


}
