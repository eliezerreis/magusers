package com.magmutual.magusers.aspect;

import com.magmutual.magusers.dto.UserDTO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


/**
 * This aspect send notification to the queue informing all microservices that new user was created.
 */
@Aspect
@Component
@Profile("dev")
public class UserNotificationAspect {
    Logger logger = LoggerFactory.getLogger(UserNotificationAspect.class);

    private final StreamBridge streamBridge;

    @Autowired
    public UserNotificationAspect(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Pointcut("execution(* com.magmutual.magusers.service.UserServiceImpl.createUser(..))")
    public void createUser() {}

    @AfterReturning(value = "createUser()", returning = "result")
    public void afterCreateCustomer(UserDTO result) {
        streamBridge.send("new-user",  result);
        logger.info("Customer notifications sent ...");
    }

}
