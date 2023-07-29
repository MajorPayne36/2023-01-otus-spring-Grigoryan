package ru.otus.eba.config;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Аспект для логгирования.
 */
@Aspect
@Component
@Log4j2
public class LoggingAspect {
    /**
     * Вывод в лог до начала метода.
     * Логгирует все public не void методы.
     * Смотрит в пакете ru.otus.eba.service все классы, заканчивающиеся на Service.
     *
     * @param joinPoint информация о вызове.
     */
    @Before("execution(* ru.otus.eba.service.*Service*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        StringBuilder sbStart = new StringBuilder();
        sbStart.append("start method: ").append(joinPoint.getSignature()).append(" {");
        for (Object arg : joinPoint.getArgs()) {
            sbStart.append(" ").append(Optional.ofNullable(arg).orElse("null")).append(" ");
        }
        sbStart.append("}");
        log.debug(sbStart.toString());
    }

    /**
     * Вывод в лог после отрабатывания метода.
     * Логгирует все public не void методы.
     * Смотрит в пакете ru.otus.eba.service все классы, заканчивающиеся на Service.
     *
     * @param joinPoint информация о вызове.
     */
    @AfterReturning(
            pointcut = "execution(* ru.otus.eba.service.*Service*.*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        StringBuilder sbStart = new StringBuilder();
        sbStart.append("end method: ").append(joinPoint.getSignature()).append(" ");
        sbStart.append("result: ").append(Optional.ofNullable(result).orElse("null"));
        log.debug(sbStart.toString());
    }
}
