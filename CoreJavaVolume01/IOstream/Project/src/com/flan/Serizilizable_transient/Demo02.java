package com.flan.Serizilizable_transient;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Demo02 {
    public static void main(String[] args) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Day10/src/com/flan/stu.txt"));

        Student stu = (Student) ois.readObject();
        System.out.println(stu);

        System.out.println(stu.getIdCard());

        ois.close();
    }
}
