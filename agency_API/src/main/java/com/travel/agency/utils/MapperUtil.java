package com.travel.agency.utils;

import com.travel.agency.interfaces.Converter;

import java.util.function.Function;


public class MapperUtil {

    public static <T, D> D mapperEntity(T entity, Function<T, D> converter) {
        return converter.apply(entity);
    }
}
