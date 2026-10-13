package BEjava.userAPI.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PackageLoggingBeanPostProcessor implements BeanPostProcessor {

    private static final String BASE_PACKAGE = "com.example.usertask";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().getName().startsWith(BASE_PACKAGE)) {
            log.info("[BPP] BEFORE init: {}", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().getName().startsWith(BASE_PACKAGE)) {
            log.info("[BPP] AFTER init: {}", beanName);
        }
        return bean;
    }
}
