package com.flan.PropertiesDemo;

import java.util.Properties;
import java.util.Set;

public class Demo01 {
    public static void main(String[] args) {
        Properties prop = new Properties();

        prop.setProperty("name", "jack");
        prop.setProperty("age", "20");
        prop.setProperty("gender", "男");
        System.out.println(prop);

        System.out.println(prop.getProperty("age"));
        System.out.println(prop.getProperty("gender"));

        System.out.println(prop);

        Set<String> set = prop.stringPropertyNames();
        for (String key : set) {
            System.out.println(key + " = " + prop.getProperty(key));
        }
    }
}

/*
jack
20
男
20
男
jack
20
男
name = jack
age = 20
gender = 男
 */
