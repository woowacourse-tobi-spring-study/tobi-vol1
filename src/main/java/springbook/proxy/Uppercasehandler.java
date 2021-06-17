package springbook.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Uppercasehandler implements InvocationHandler {

    private Hello target;

    public Uppercasehandler(Hello target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = (String) method.invoke(target, args);
        return result.toUpperCase();
    }
}
