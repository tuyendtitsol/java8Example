package com.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.INFO;

public class PredicateSample {

    private static Logger logger;

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
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> listString = Arrays.asList("A", "AA", "AAA", "B", "BB", "BBB");

        Predicate<String> lengthIs3 = x -> x.length() == 3;
        Predicate<String> startWithA = x -> x.startsWith("A");
        Predicate<Integer> noGreaterThan5 =  x -> x > 5;
        Predicate<Integer> noLessThan8 = x -> x < 8;
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();

        List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
        logger.info(nonEmpty.size() + "");

        // 1. predicate in filter()
        predicateInFilter(list, noGreaterThan5);

        // 2. Predicate.and()
        predicateAndMethod(list, noGreaterThan5, noLessThan8);

        // 3. Predicate.or()
        predicateOrMethod(listString, lengthIs3, startWithA);

        // 4. Predicate.negate()
        predicateNegateMethod(listString, startWithA);

        // 5. Predicate.test() in function
        predicateInFunction(listString);

        // 6. Predicate Chaining
        predicateChaining(startWithA);

        // 7. Predicate in Object
        predicateInObject();
    }

    private static void predicateChaining(Predicate<String> startWithA) {
        // We can chain predicates together.
        // start with "A" or "m"
        boolean result = startWithA.or(x -> x.startsWith("m")).test("mkyong");
        System.out.println(result);     // true

        // !(start with "A" and length is 3)
        boolean result2 = startWithA.and(x -> x.length() == 3).negate().test("Abc");
        System.out.println(result2);
    }

    private static void predicateInFunction(List<String> listString) {
        // Predicate in function.
        System.out.println(StringProcessor.filter(
                listString, x -> x.startsWith("A")));                    // [A, AA, AAA]

        System.out.println(StringProcessor.filter(
                listString, x -> x.startsWith("A") && x.length() == 3)); // [AAA]
    }

    private static void predicateNegateMethod(List<String> listString, Predicate<String> startWithA) {
        // Find all elements not start with ‘A’.
        List<String> collect6 = listString.stream()
                .filter(startWithA.negate())
                .collect(Collectors.toList());
        System.out.println("Filter using predicate.negate() " + collect6);
    }

    private static void predicateOrMethod(List<String> listString, Predicate<String> lengthIs3, Predicate<String> startWithA) {
        List<String> collect5 = listString.stream()
                .filter(lengthIs3.or(startWithA))
                .collect(Collectors.toList());
        System.out.println("Multiple filters using predicate.or:" + collect5);
    }

    private static void predicateAndMethod(List<Integer> list, Predicate<Integer> noGreaterThan5, Predicate<Integer> noLessThan8) {
        // Multiple filters.
        List<Integer> collect3 = list.stream()
                .filter(x -> x > 5 && x < 8).collect(Collectors.toList());
        System.out.println("Multiple filters using lambda:" + collect3);

        List<Integer> collect4 = list.stream()
                .filter(noGreaterThan5.and(noLessThan8))
                .collect(Collectors.toList());
        System.out.println("Multiple filters using predicate: " + collect4);
    }

    private static void predicateInFilter(List<Integer> list, Predicate<Integer> noGreaterThan5) {
        // filter() accepts predicate as argument.
        List<Integer> collect = list.stream().filter(x -> x>5).collect(Collectors.toList());
        logger.log(INFO, "Filter using lambda: {}", collect);

        collect = list.stream()
                .filter(noGreaterThan5)
                .collect(Collectors.toList());
        System.out.println("Filter using predicate: " + collect); // [6, 7, 8, 9, 10]
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
