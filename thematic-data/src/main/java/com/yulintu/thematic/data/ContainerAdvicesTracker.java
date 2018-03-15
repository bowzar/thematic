package com.yulintu.thematic.data;

import com.yulintu.thematic.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
public class ContainerAdvicesTracker {

    private static final Logger logger = LoggerFactory.getLogger(ContainerAdvicesTracker.class);

    @Around("@within(com.yulintu.thematic.data.Trackable)")
    public Object onAround(ProceedingJoinPoint pjp) {

        try {

            return pjp.proceed(pjp.getArgs());

        } catch (Throwable e) {
            logger.error(buildMessage(pjp, e), e);
            ExceptionUtils.throwRuntimeException(e);
            return null;
        }
    }

    private String buildMessage(ProceedingJoinPoint pjp, Throwable e) {

        String name = pjp.getSignature().toString();
        StringBuilder builder = new StringBuilder(String.format("执行方法 %s 的过程中发生了异常. ", name));

        Object[] args = pjp.getArgs();
        if (args.length == 0) {
            return builder.toString();
        }

        builder.append("参数为");
        builder.append(String.join(", ", Arrays.stream(args)
                .map(c -> c == null ? "null" : c.toString())
                .map(c -> c.length() > 100 ? c.substring(0, 99) : c)
                .collect(Collectors.toList())));

        return builder.toString();
    }
}
