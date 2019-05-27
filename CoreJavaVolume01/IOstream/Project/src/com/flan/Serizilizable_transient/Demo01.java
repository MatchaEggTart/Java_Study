package com.flan.Serizilizable_transient;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 不需要 serialVersionUID transient 都可以运行， 需要测试能否读取
    可以的
 */
public class Demo01 {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Day10/src/com/flan/stu.txt"));

        Student stu = new Student("Axel", 21);
        stu.setIdCard("111222");

        oos.writeObject(stu);

        oos.close();
    }
}
