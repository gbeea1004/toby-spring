package geon.hee.tobyspring.study;

public interface LineCallback<T> {

    T doSomethingWithLine(String line, T value);
}
