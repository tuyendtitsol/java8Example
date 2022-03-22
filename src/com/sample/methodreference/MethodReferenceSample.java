package com.sample.methodreference;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

public class MethodReferenceSample {
    static List<String> messages = Arrays.asList("hello", "baeldung", "readers!");

    static <V,T,U> BiConsumer<V,U> filterFirstArg(BiConsumer<T,U> c, Function<V,T> f) {
        return (t,u)->c.accept(f.apply(t), u);
    }

    public static void main(String[] args) {
        referenceStaticMethod();
        List<String> str = Arrays.asList("a","b","A","B");
        str.sort(String::compareToIgnoreCase);
        BiPredicate<List<String>, String> contains = List::contains;
    }

    private static void referenceStaticMethod() {
        // using lambdas
//        messages.forEach(word -> System.out.println(StringUtils.capitalize(word)));
//        messages.forEach(word -> System.out.println(word));

        // using method reference
//        messages.forEach(MethodReferenceSample::accept);

//        Consumer<String> consumer = StringUtils::capitalize;
        Consumer<String> consumer = StringUtils::capitalize;
        forEach(messages, System.out::println);
        forEach(messages, consumer.andThen(System.out::println));
    }

    private static void accept(String word) {
        System.out.println(StringUtils.capitalize(word));
    }

    static <T> void forEach(List<String> list, Consumer<String> consumer) {
        for (String t : list) {
            consumer.accept(t);
        }
    }
}
