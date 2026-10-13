package BEjava.userAPI.controller;

import BEjava.userAPI.component.RequestContext;
import BEjava.userAPI.component.UserAuditLog;
import BEjava.userAPI.service.UserInfoService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/scope-demo")
public class ScopeDemoController {

    private final UserInfoService userInfoService;
    private final ObjectProvider<UserAuditLog> auditLogProvider;
    private final RequestContext requestContext;

    public ScopeDemoController(UserInfoService userInfoService,
                               ObjectProvider<UserAuditLog> auditLogProvider,
                               RequestContext requestContext) {
        this.userInfoService = userInfoService;
        this.auditLogProvider = auditLogProvider;
        this.requestContext = requestContext;
    }

    @GetMapping
    public Map<String, Object> demo() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("singletonHashCode", System.identityHashCode(userInfoService));
        result.put("prototypeAuditId1", auditLogProvider.getObject().getAuditId());
        result.put("prototypeAuditId2", auditLogProvider.getObject().getAuditId());
        result.put("requestId", requestContext.getRequestId());
        return result;
    }
}
