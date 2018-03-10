package com.yulintu.thematic.data;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
public class ConnectionAdvices {

    @Around("this(com.yulintu.thematic.data.Service)")
    public Object onAroundConnection(ProceedingJoinPoint pjp) {

        Provider provider = null;
        try {

            provider = ProviderUtils.getCurrentProvider();
            if (provider == null) {
                ProviderUtils.setCurrentProvider(
                        provider = GlobalApplicationContext.getApplicationContext().getBean(Provider.class));
            }
            if (provider instanceof ProviderHibernate) {
                ((ProviderHibernate) provider).openConnection();
            }

            return pjp.proceed();

        } catch (Throwable e) {
            throw new RuntimeException(e);

        } finally {
            if (provider instanceof ProviderHibernate) {
                ((ProviderHibernate) provider).closeConnection();
            }
        }
    }

    @Around("@annotation(com.yulintu.thematic.data.Transaction)")
    public Object onAroundTransaction(ProceedingJoinPoint pjp) {

        ProviderHibernate provider = null;
        try {

            Provider p = ProviderUtils.getCurrentProvider();
            if (p == null) {
                ProviderUtils.setCurrentProvider(
                        p = GlobalApplicationContext.getApplicationContext().getBean(Provider.class));
            }
            if (p instanceof ProviderHibernate) {
                provider = (ProviderHibernate) p;
                provider.openConnection();
                provider.beginTransaction();
            }

            Object val = pjp.proceed();
            if (provider != null)
                provider.commitTransaction();

            return val;

        } catch (Throwable e) {
            if (provider != null)
                provider.rollbackTransaction();

            throw new RuntimeException(e);

        } finally {
            if (provider != null)
                provider.closeConnection();
        }
    }
}
