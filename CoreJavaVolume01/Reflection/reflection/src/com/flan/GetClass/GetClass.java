package com.flan.GetClass;

import com.flan.Employee.Employee;
import com.flan.Manager.Manager;

public class GetClass {
    public static void main(String[] args) {
        Employee[] e = new Employee[3];
        Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        e[0] = boss;
        e[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        e[2] = new Employee("Tommy Tester", 40000, 1990, 3, 15);
        Class cl_e1 = e[1].getClass();
        Class cl_e0 = e[0].getClass();

        System.out.println("Show the array :");
        for (Employee element : e) {
            System.out.println("name :  " + element.getName() + "   salary :  " + element.getSalary());
        }

        System.out.println("---------------------------------");


        System.out.println("---------------------------------");
        System.out.println("print getClass");
        for (Employee element : e) {
            System.out.println(element.getClass());
        }
        System.out.println("---------------------------------");
        System.out.println(cl_e1.getName() + " " + e[1].getName());
        System.out.println(cl_e0.getName() + " " + e[2].getName());

        System.out.println("---------------------------------");

        Manager m = new Manager("Carl Cracker", 8000, 1987, 12, 15);

        String className = m.getClass().getName();
        Class cl_m;
        try {
            cl_m = Class.forName(className);
            System.out.println(cl_m.getName());
        } catch (ClassNotFoundException exception) {
            System.out.println("Class not found :");
            System.out.println(exception.getStackTrace());
        }


    }
}
