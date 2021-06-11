package springbook.learningtest.template;

import org.springframework.jdbc.core.JdbcTemplate;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
