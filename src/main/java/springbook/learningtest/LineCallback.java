package springbook.learningtest;

public interface LineCallback<T> {

    public T doSomething(String line, T value);
}
