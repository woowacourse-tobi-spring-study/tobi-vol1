package springbook.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

class HelloTest {

    @Test
    void simpleProxy() {
        Hello hello = new HelloUppercase(new HelloTarget());

        assertThat(hello.sayHello("abc")).isEqualTo("HELLOABC");
        assertThat(hello.sayHi("abc")).isEqualTo("HIABC");
        assertThat(hello.sayThankYou("abc")).isEqualTo("THANK YOUABC");
    }

    @Test
    void proxyWithReflection() {
        Hello proxy = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{Hello.class}, new Uppercasehandler(new HelloTarget()));

        assertThat(proxy.sayHello("abc")).isEqualTo("HELLOABC");
        assertThat(proxy.sayHi("abc")).isEqualTo("HIABC");
        assertThat(proxy.sayThankYou("abc")).isEqualTo("THANK YOUABC");
    }

    @Test
    void proxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        Hello proxy = (Hello) proxyFactoryBean.getObject();
        assertThat(proxy.sayHello("abc")).isEqualTo("HELLOABC");
        assertThat(proxy.sayHi("abc")).isEqualTo("HIABC");
        assertThat(proxy.sayThankYou("abc")).isEqualTo("THANK YOUABC");
    }

    @Test
    void pointcutAdvisor() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

        Hello proxy = (Hello) proxyFactoryBean.getObject();
        assertThat(proxy.sayHello("abc")).isEqualTo("HELLOABC");
        assertThat(proxy.sayHi("abc")).isEqualTo("HIABC");
        assertThat(proxy.sayThankYou("abc")).isNotEqualTo("THANK YOUABC");
    }

    @Test
    void classNamePointcutAdvisor() {
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return clazz.getSimpleName().startsWith("HelloT");
                    }
                };
            }
        };
        nameMatchMethodPointcut.setMappedName("sayH*");

        checkAdviced(new HelloTarget(), nameMatchMethodPointcut, true);
        checkAdviced(new HelloWorld(), nameMatchMethodPointcut, false);
        checkAdviced(new HelloToby(), nameMatchMethodPointcut, true);

    }

    private void checkAdviced(Hello hello, NameMatchMethodPointcut nameMatchMethodPointcut, boolean isAdviced) {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(hello);
        proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(nameMatchMethodPointcut, new UppercaseAdvice()));
        Hello proxy = (Hello) proxyFactoryBean.getObject();

        if (isAdviced) {
            assertThat(proxy.sayHello("abc")).isEqualTo("HELLOABC");
            assertThat(proxy.sayHi("abc")).isEqualTo("HIABC");
            assertThat(proxy.sayThankYou("abc")).isNotEqualTo("THANK YOUABC");
        } else {
            assertThat(proxy.sayHello("abc")).isNotEqualTo("HELLOABC");
            assertThat(proxy.sayHi("abc")).isNotEqualTo("HIABC");
            assertThat(proxy.sayThankYou("abc")).isNotEqualTo("THANK YOUABC");
        }
    }


    static class HelloWorld extends HelloTarget {
    }


    static class HelloToby extends HelloTarget {
    }

    static class UppercaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }
}
