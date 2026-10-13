package BEjava.userAPI.controller;

import BEjava.userAPI.component.UserStatistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {

    private final UserStatistics userStatistics;

    public StatsController(UserStatistics userStatistics) {
        this.userStatistics = userStatistics;
    }

    @GetMapping
    public Map<String, Long> stats() {
        return Map.of(
                "createdUsers", userStatistics.getCreatedUsers(),
                "getUserByIdCalls", userStatistics.getGetUserByIdCalls()
        );
    }
}
