package com.binghe.ch02.learningtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 학습 테스트 내용
 * - Spring 테스트용 애플리케이션 컨텍스트는 기본 JUnit와 다르게 한 개만 만들어진다.
 * - 각 메서드는 기존의 JUnit과 동일하게 매번 새로운 객체를 만들지만, 애플리케이션 컨텍스트는 하나만 만든다.
 */

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class SpringContextTest {

    @Autowired
    ApplicationContext context;

    static Set<SpringContextTest> testObjects = new HashSet<>();
    static ApplicationContext contextObject = null;

    @Test
    void test1() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context)
            .isTrue();
        contextObject = this.context;
    }

    @Test
    void test2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context)
            .isTrue();
        contextObject = this.context;
    }

    @Test
    void test3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);

        assertThat(contextObject == null || contextObject == this.context)
            .isTrue();
        contextObject = this.context;
    }
}
