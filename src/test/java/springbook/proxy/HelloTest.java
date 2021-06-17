package springbook.proxy;

import org.junit.jupiter.api.Test;

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
}
