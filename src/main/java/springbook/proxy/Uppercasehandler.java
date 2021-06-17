package springbook.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Uppercasehandler implements InvocationHandler {

    private Object target;

    public Uppercasehandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object object = method.invoke(target, args);
        if (object instanceof String && method.getName().startsWith("say")) {
            return ((String) object).toUpperCase();
        }
        return object;
    }
}
