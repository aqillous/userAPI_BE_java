package BEjava.userAPI.component;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@SessionScope
public class RecentlyViewedUsers {
    private static final int MAX_SIZE = 5;
    private final List<Long> users = new ArrayList<>();

    public RecentlyViewedUsers() {
        log.info("RecentlyViewedUsers yaradildi (yeni session): {}", this.hashCode());
    }

    public synchronized void addUser(Long userId){
        users.remove(userId);
        users.add(0 , userId);
        if (users.size() > MAX_SIZE){
            users.remove(MAX_SIZE);
        }

    }

    public synchronized List<Long> getUsers(){
        return List.copyOf(users);
    }

    @PreDestroy
    public void destroy() {
        log.info("RecentlyViewedUsers destroy olunur (session bitdi): {}", users);
    }

}
