package com.hvdbs.savra.StepicMiddleJavaDeveloperMtsBank.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LogExecutionTimePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserService userService) {
            Set<? extends Class<? extends Annotation>> annotationTypes = Arrays.stream(userService.getClass().getDeclaredMethods())
                    .flatMap(method -> Arrays.stream(method.getAnnotations()))
                    .map(Annotation::annotationType).collect(Collectors.toSet());

            if (annotationTypes.contains(LogExecutionTime.class)) {
                bean = new LogExecutionTimeUserService((UserService) bean);
            }

            if (annotationTypes.contains(Auditable.class)) {
                bean = new AuditableUserServiceImpl((UserService) bean);
            }
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
