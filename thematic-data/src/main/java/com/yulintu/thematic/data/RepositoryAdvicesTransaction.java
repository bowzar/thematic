package com.yulintu.thematic.data;

import com.yulintu.thematic.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RepositoryAdvicesTransaction {

    @Around("@annotation(com.yulintu.thematic.data.DbTransaction)")
    public Object onAround(ProceedingJoinPoint pjp) {

        Provider provider = null;
        Object[] args = pjp.getArgs();
        Object obj = pjp.getThis();

        try {

            if (obj instanceof Repository) {
                provider = ((Repository) obj).getProvider();
                if (provider instanceof ProviderDb) {
                    ((ProviderDb) provider).beginTransaction();
                }
            }

            Object val = pjp.proceed(args);

            if (provider instanceof ProviderDb) {
                ((ProviderDb) provider).commitTransaction();
            }

            return val;

        } catch (Throwable e) {
            if (provider instanceof ProviderDb) {
                ((ProviderDb) provider).rollbackTransaction();
            }

            ExceptionUtils.throwRuntimeException(e);
            return null;

        }
    }
}
