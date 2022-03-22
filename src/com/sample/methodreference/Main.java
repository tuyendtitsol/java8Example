package com.sample.methodreference;/*from   w  w  w  .java2 s.  c  o  m*/

import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student(1, 3, "John"),
                new Student(2, 4, "Jane"),
                new Student(3, 3, "Jack"));

        Consumer<Student> raiser = e -> {
            e.gpa = e.gpa * 1.1;
        };

        raiseStudents(students, System.out::println);
        raiseStudents(students, raiser.andThen(System.out::println));


        // constructor references
        // without parameter. Using supplier class and get() method
        Supplier<Student> c1 = Student::new;
        Student s1 = c1.get();
        s1.setGpa(9.9);
        System.out.println(s1);
        Consumer c = System.out::println;
        c.accept(s1);
        Runnable runnable = () -> System.out.println(s1);
        runnable.run();

        // with parameters. Using Function class and apply() method
        Function<String, Student> c2 = Student::new;
        Student s2 = c2.apply("vinci");
        runnable = () -> System.out.println(s2);
        runnable.run();
    }

    private static void raiseStudents(List<Student> employees,
                                      Consumer<Student> fx) {
        for (Student e : employees) {
            fx.accept(e);
        }
    }

}

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

class Student {
    public int id;
    public double gpa;
    public String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ">" + name + ": " + gpa;
    }
}