package com.flan.SerizlizableDemo;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Demo02 {
    public static void main(String[] args) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("stu.txt"));

        Student stu = (Student) ois.readObject();
        System.out.println(stu);

        ois.close();
    }
}
