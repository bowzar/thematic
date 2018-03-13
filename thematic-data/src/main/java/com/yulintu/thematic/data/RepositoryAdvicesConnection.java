package com.yulintu.thematic.data;

import com.yulintu.thematic.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RepositoryAdvicesConnection {

    @Around("@within(com.yulintu.thematic.data.DbConnection)")
    public Object onAround(ProceedingJoinPoint pjp) {

        Provider provider = null;
        Object[] args = pjp.getArgs();
        Object obj = pjp.getThis();

        try {

            if (obj instanceof Repository) {
                provider = ((Repository) obj).getProvider();
                if (provider instanceof ProviderDb) {
                    ((ProviderDb) provider).openConnection();
                }
            }

            return pjp.proceed(args);

        } catch (Throwable e) {
            ExceptionUtils.throwRuntimeException(e);
            return null;

        } finally {
            if (provider instanceof ProviderDb) {
                ((ProviderDb) provider).closeConnection();
            }
        }
    }
}
