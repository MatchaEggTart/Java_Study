package com.flan.StringDemo;

import jdk.swing.interop.SwingInterOpUtils;

public class StringDemo {
    public static void main(String[] args) {
        String str = "HelloWorld";
        String s = str.substring(0, 3);
        System.out.println(s);

        System.out.println("-------------------------------");

        String hello = "Hello ";
        String world = "World";
        String message = hello + world;
        System.out.println(message);

        System.out.println("-------------------------------");

        int age = 13;
        String rating = "PG" + age;
        System.out.println(rating);

        System.out.println("-------------------------------");

        String all = String.join(" / ", "S", "M", "L", "XL");
        System.out.println(all);

        System.out.println("-------------------------------");

        String a = "Hello";
        String b = a;
        System.out.println(b);

        System.out.println("--------------------------------");

        String c = "HelloWorld";
        String d = c.substring(0, 5) + "M" + c.substring(6);
        System.out.println(d);

        System.out.println("--------------------------------");

        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = "Hello!";
        System.out.println(str1.equals(str2));
        System.out.println(str1.equals(str3));

        System.out.println("--------------------------------");

        str = "";

        if (str.length() == 0) {
            System.out.println("str is a empty String.");
        }

        if (str.equals("")) {
            System.out.println("str is a empty String");
        }

        str = null;

        if (str == null) {
            System.out.println("str prointer nowhere");
        }

        System.out.println("--------------------------------");

        String greeting = "Hello";
        int n = greeting.length();
        System.out.println("n = " + n);

        int cpCount = greeting.codePointCount(0, greeting.length());
        System.out.println("cpCount = " + cpCount);

        char first = greeting.charAt(0);
        char last = greeting.charAt(4);
        System.out.println("first = " + first + "\tlast  = " + "last");

        int index = greeting.offsetByCodePoints(0, 3);
        int cp = greeting.codePointAt(index);
        System.out.println("index = " + index);
        System.out.println("cp    = " + cp);
    }
}
