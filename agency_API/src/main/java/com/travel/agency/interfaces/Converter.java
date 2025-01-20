package com.travel.agency.interfaces;

@FunctionalInterface
public interface Converter<T, U> {
    U convert(T t);
}
