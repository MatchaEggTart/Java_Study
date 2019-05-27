package com.flan.PropertiesDemo;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class Demo03 {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        //write();
        readAll();
    }

    private static void write() throws IOException {
        Properties properties = new Properties();
        String name;
        String age;
        String gender;

        FileWriter writer = new FileWriter("Day09/user.properties", true);
        System.out.println("Name");
        name = scan.nextLine();
        System.out.println("Age");
        age = scan.nextLine();
        System.out.println("Gender");
        gender = scan.nextLine();

        properties.setProperty("name   ", name);
        properties.setProperty("age    ", age);
        properties.setProperty("gender ", gender);

        properties.store(writer, null);

        writer.close();

    }

    private static void read() throws IOException {
        Properties prop = new Properties();

        FileReader reader = new FileReader("Day09/user.properties");


        int i = 0;

        prop.load(reader);

        Set<String> keySet = prop.stringPropertyNames();
        System.out.println();
        for (String key : keySet) {
            System.out.println(key + " -- " + prop.getProperty(key));
            if (++i % 3 == 0)
                System.out.println("------------------------");
        }

        reader.close();
    }

    // Properties 的 load方法 会因为 键相同 被取代， 所以只能存储文件中的一个值， 我觉得需要数组来跑或者 Collection
    private static void readAll() throws IOException {
        // Properties props = new Properties();

        FileReader reader = new FileReader("Day09/user.properties");

        int i = 0;
        //prop.load(reader);
        int len = -1;

        while ((len = reader.read()) != -1) {
            System.out.print((char)len);
        }

        reader.close();
    }
}
