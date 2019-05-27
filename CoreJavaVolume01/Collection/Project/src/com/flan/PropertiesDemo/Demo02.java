package com.flan.PropertiesDemo;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class Demo02 {
    public static void main(String[] args) throws Exception {
        read();
        write();
        read();
    }

    private static void read() throws Exception {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("Day09/user.properties");
        prop.load(fis);
        fis.close();

        Set<String> keySet = prop.stringPropertyNames();
        for (String key : keySet) {
            System.out.println(key + " - " + prop.getProperty(key));
        }
    }

    private static void write() throws IOException {
        Properties prop = new Properties();
        prop.setProperty("name", "Axel");
        prop.setProperty("gender", "男");
        prop.setProperty("age", "20");

        FileOutputStream fos = new FileOutputStream("Day09/user.properties", true);
        prop.store(fos, null);

        fos.close();
    }
}
