package springbook.learningtest;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.assertj.core.api.Assertions.assertThat;

class PointcutTest {

    @Test
    void methodSignaturePointcut() throws NoSuchMethodException {

        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression("execution(public int springbook.learningtest.PointcutTest$Target.minus(int, int) throws java.lang.RuntimeException)");

        assertThat(aspectJExpressionPointcut.getClassFilter().matches(Target.class) &&
                aspectJExpressionPointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null))
                .isTrue();

        assertThat(aspectJExpressionPointcut.getClassFilter().matches(Target.class) &&
                aspectJExpressionPointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null))
                .isFalse();

        assertThat(aspectJExpressionPointcut.getClassFilter().matches(Bean.class) &&
                aspectJExpressionPointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null))
                .isFalse();
    }

    interface TargetInterface {
        void hello();

        void hello(String a);

        int minus(int a, int b);

        int plus(int a, int b);
    }

    static class Target implements TargetInterface {

        public void hello() {
        }

        public void hello(String a) {
        }

        public int minus(int a, int b) throws RuntimeException {
            return 0;
        }

        public int plus(int a, int b) {
            return 0;
        }

        public void method() {
        }
    }

    static class Bean {

        public void method() throws RuntimeException {
        }
    }
}
