package com.flan.Serizilizable_transient;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private transient String idCard;
    // private int kk;

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(String name, int age, String idCard) {
        this.name = name;
        this.age = age;
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}