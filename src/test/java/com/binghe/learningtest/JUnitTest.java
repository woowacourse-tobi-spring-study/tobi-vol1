package com.binghe.learningtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * 학습 테스트 내용
 * - JUnit은 매 메서드마다 새로운 객체를 만들어서 테스트한다.
 */
public class JUnitTest {

    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    void test1() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
    }

    @Test
    void test2() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
    }

    @Test
    void test3() {
        assertThat(testObjects).doesNotContain(this);
        testObjects.add(this);
    }
}
