package com.example.java_spring_boot.aop;

import com.example.java_spring_boot.enums.ActionType;
import com.example.java_spring_boot.service.MailService;
import com.example.java_spring_boot.util.UserIdentity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class SendEmailAspect {

    private UserIdentity userIdentity;
    private MailService mailService;

    private static final Map<ActionType, String> SUBJECT_TEMPLATE_MAP;
    private static final Map<ActionType, String> MESSAGE_TEMPLATE_MAP;

    static {
        SUBJECT_TEMPLATE_MAP = new EnumMap<>(ActionType.class);
        SUBJECT_TEMPLATE_MAP.put(ActionType.CREATE, "Create %s");
        SUBJECT_TEMPLATE_MAP.put(ActionType.UPDATE, "Update %s");
        SUBJECT_TEMPLATE_MAP.put(ActionType.DELETE, "Delete %s");

        MESSAGE_TEMPLATE_MAP = new EnumMap<>(ActionType.class);
        MESSAGE_TEMPLATE_MAP.put(ActionType.CREATE, "Hi %s, There's a new %s (%s) created.");
        MESSAGE_TEMPLATE_MAP.put(ActionType.UPDATE, "Hi %s, There's a %s (%s) updated.");
        MESSAGE_TEMPLATE_MAP.put(ActionType.DELETE, "Hi %s, A %s (%s) is just deleted.");
    }

    SendEmailAspect(UserIdentity userIdentity, MailService mailService) {
        this.userIdentity = userIdentity;
        this.mailService = mailService;
    }

    @Pointcut("@annotation(com.example.java_spring_boot.aop.SendEmail)")
    public void pointcut() {}

    @AfterReturning(pointcut = "pointcut()", returning = "result")
    public void sendEmail(JoinPoint joinPoint, Object result) {
        if (userIdentity.isAnonymous()) {
            return;
        }

        SendEmail annotation = getAnnotation(joinPoint);
        String subject = composeSubject(annotation);
        String message = composeMessage(annotation, joinPoint, result);

        mailService.sendMail(subject, message, List.of(userIdentity.getUsername()));
    }

    private SendEmail getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SendEmail.class);
    }

    private String composeSubject(SendEmail annotation) {
        String template = SUBJECT_TEMPLATE_MAP.get(annotation.actionType());
        return String.format(template, annotation.entityType());
    }

    private String composeMessage(SendEmail annotation, JoinPoint joinPoint, Object entity) {
        String template = MESSAGE_TEMPLATE_MAP.get(annotation.actionType());

        int idParamIdentity = annotation.idParamIdentity();
        String entityId = idParamIdentity == -1 ? getEntityId(entity) : (String) joinPoint.getArgs()[idParamIdentity];

        return String.format(template, userIdentity.getUsername(), annotation.entityType(), entityId);
    }

    private String getEntityId(Object obj) {
        try {
            Field field = obj.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return (String) field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return "";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }
}
