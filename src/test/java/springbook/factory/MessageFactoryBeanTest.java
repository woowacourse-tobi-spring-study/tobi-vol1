package springbook.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:test-applicationContext.xml")
class MessageFactoryBeanTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void messageFactoryBean() {
        Object object = applicationContext.getBean("message");
        Object factory = applicationContext.getBean("&message");

        assertThat(object).isInstanceOf(Message.class);
        assertThat(((Message) object).getText()).isEqualTo("Factory Bean");
        assertThat(factory).isInstanceOf(MessageFactoryBean.class);
    }
}
