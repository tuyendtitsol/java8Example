package com.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


//@FunctionalInterface
//interface Function<T, R> {
//    R apply(T t);
//}

public class FunctionSample {
    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for (T s : list) {
            result.add(f.apply(s));
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> l = map(Arrays.asList("lambdas", "in", "action"),
                String::length);
        l.forEach(a -> System.out.println(a + ""));
    }
}



