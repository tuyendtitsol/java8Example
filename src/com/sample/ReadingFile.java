package com.sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadingFile {

    public static void main(String... args) throws IOException {
        System.out.println("JAVA 7");
        System.out.println(processFile());

        System.out.println("JAVA 8");
        String oneLine = processFile( (BufferedReader br) -> br.readLine() );
        System.out.println(oneLine);
        String twoLines = processFile( (BufferedReader br) -> br.readLine() + " - " + br.readLine());
        System.out.println(twoLines);
    }

    // java 7
    public static String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return br.readLine();
        }
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    // java 8 using lambda and behavior parameterization
    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
            return p.process(br);
        }
    }
}
