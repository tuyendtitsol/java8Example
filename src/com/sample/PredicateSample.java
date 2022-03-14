package com.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateSample {

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T s: list){
            if(p.test(s)){
                results.add(s);
            }
        }
        return results;
    }

    public static void main(String[] args) {
        List<String> listOfStrings = Arrays.asList("A", "B", "", "C", "");
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
        System.out.println(nonEmpty.size());

        // 1. predicate in filter()
        // filter() accepts predicate as argument.
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> collect = list.stream().filter(x -> x>5).collect(Collectors.toList());
        System.out.println("Filter using lambda:" + collect);
        Predicate<Integer> noGreaterThan5 =  x -> x > 5;
        List<Integer> collect1 = list.stream()
                .filter(noGreaterThan5)
                .collect(Collectors.toList());
        System.out.println("Filter using predicate: " + collect1); // [6, 7, 8, 9, 10]


        // 2.Predicate.and()
        // Multiple filters.
        List<Integer> collect3 = list.stream()
                .filter(x -> x > 5 && x < 8).collect(Collectors.toList());
        System.out.println("Multiple filters using lambda:" + collect3);

        Predicate<Integer> noLessThan8 = x -> x < 8;
        List<Integer> collect4 = list.stream()
                .filter(noGreaterThan5.and(noLessThan8))
                .collect(Collectors.toList());
        System.out.println("Multiple filters using predicate: " + collect4);

        // Predicate.or()
        Predicate<String> lengthIs3 = x -> x.length() == 3;
        Predicate<String> startWithA = x -> x.startsWith("A");
        List<String> listString = Arrays.asList("A", "AA", "AAA", "B", "BB", "BBB");
        List<String> collect5 = listString.stream()
                .filter(lengthIs3.or(startWithA))
                .collect(Collectors.toList());
        System.out.println("Multiple filters using predicate.or:" + collect5);

        // 4. Predicate.negate()
        // Find all elements not start with ‘A’.
        List<String> collect6 = listString.stream()
                .filter(startWithA.negate())
                .collect(Collectors.toList());
        System.out.println("Filter using predicate.negate() " + collect6);

        // 5. Predicate.test() in function
        // Predicate in function.
        System.out.println(StringProcessor.filter(
                listString, x -> x.startsWith("A")));                    // [A, AA, AAA]

        System.out.println(StringProcessor.filter(
                listString, x -> x.startsWith("A") && x.length() == 3)); // [AAA]

        // 6. Predicate Chaining
        // We can chain predicates together.
        // start with "A" or "m"
        boolean result = startWithA.or(x -> x.startsWith("m")).test("mkyong");
        System.out.println(result);     // true

        // !(start with "A" and length is 3)
        boolean result2 = startWithA.and(x -> x.length() == 3).negate().test("Abc");
        System.out.println(result2);

        // 7. Predicate in Object
        predicateInObject();
    }

    private static void predicateInObject() {
        Hosting h1 = new Hosting(1, "amazon", "aws.amazon.com");
        Hosting h2 = new Hosting(2, "linode", "linode.com");
        Hosting h3 = new Hosting(3, "liquidweb", "liquidweb.com");
        Hosting h4 = new Hosting(4, "google", "google.com");

        List<Hosting> list = Arrays.asList(new Hosting[]{h1, h2, h3, h4});

        List<Hosting> result = HostingRepository.filterHosting(list, x -> x.getName().startsWith("g"));
        System.out.println("result : " + result);  // google

        List<Hosting> result2 = HostingRepository.filterHosting(list, isDeveloperFriendly());
        System.out.println("result2 : " + result2); // linode
    }

    public static Predicate<Hosting> isDeveloperFriendly() {
        return n -> n.getName().equals("linode");
    }
}

class StringProcessor {
    static List<String> filter(List<String> list, Predicate<String> predicate) {
        return list.stream().filter(predicate::test).collect(Collectors.toList());
    }
}
