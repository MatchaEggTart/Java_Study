package com.flan.NoSerializableProblem;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
                    NotSerializableException
 Exception in thread "main" java.io.NotSerializableException: com.flan.SerializableDemo.Student
 at java.base/java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1185)
 at java.base/java.io.ObjectOutputStream.writeObject(ObjectOutputStream.java:349)
 at com.flan.SerializableDemo.Demo01.main(Demo01.java:14)
 */
public class Demo01 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("Day10/src/com/flan/stu.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        Student stu = new Student("jack", 20);
        stu.setIdCard("112345");

        oos.writeObject(stu);

        oos.close();
    }
}