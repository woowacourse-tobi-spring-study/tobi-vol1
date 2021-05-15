package springbook.dao;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LearningTest {

    private static Set<LearningTest> testObjects = new HashSet<>();
    private static ApplicationContext contextObject = null;
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @Order(1)
    void test1() {
        testObjects.add(this);
        assertThat(contextObject).isNull();
        contextObject = this.applicationContext;
    }

    @Test
    @Order(2)
    void test2() {
        testObjects.add(this);
        assertThat(contextObject).isSameAs(applicationContext);
        contextObject = this.applicationContext;
    }
}
