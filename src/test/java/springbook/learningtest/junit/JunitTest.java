package springbook.learningtest.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/junit.xml")
public class JunitTest {
    @Autowired
    ApplicationContext context;

    static Set<JunitTest> testObjects = new HashSet<>();
    static ApplicationContext contextObj = null;

    @Test
    public void test1() {
        assertThat(testObjects).isNotIn(this);
        testObjects.add(this);
        assertThat(contextObj == null || contextObj == this.context).isTrue();
        contextObj = this.context;
    }

    @Test
    public void test2() {
        assertThat(testObjects).isNotIn(this);
        testObjects.add(this);
        assertThat(contextObj == null || contextObj == this.context).isTrue();
        contextObj = this.context;
    }

    @Test
    public void test3() {
        assertThat(testObjects).isNotIn(this);
        testObjects.add(this);
        assertThat(contextObj == null || contextObj == this.context).isTrue();
        contextObj = this.context;
    }
}
