package com.flan.Pair02;

import java.time.LocalDate;

public class PairTest2 {

    public static void main(String[] args) {
        LocalDate[] birthdays = {
                LocalDate.of(1906, 12, 9),
                LocalDate.of(1815, 12, 10),
                LocalDate.of(1903, 12, 3),
                LocalDate.of(1910, 6, 22),
        };
        Pair<LocalDate> mm = ArrayAlg.minmax(birthdays);
        System.out.println("min = " + mm.getSecond());
        System.out.println("max = " + mm.getFirst());

        // Student[] b = new Student[2];
        // Pair<Student> std = ArrayAlg.minmax(b);


    }
}

class ArrayAlg {
    public static <T extends Comparable> Pair<T> minmax(T[] a) {
        if (a == null || a.length == 0) return null;

        T max = a[0];
        T min = a[0];

        for (int i = 1; i < a.length; i++) {
            if (max.compareTo(a[i]) > 0) max = a[i];
            if (min.compareTo(a[i]) < 0) min = a[i];
        }

        return new Pair<>(max, min);
    }

}
