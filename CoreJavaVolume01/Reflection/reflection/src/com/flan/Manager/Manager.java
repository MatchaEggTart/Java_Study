package com.flan.Manager;

import com.flan.Employee.Employee;

import java.time.LocalDate;

public class Manager extends Employee {
    private double bonus;

    public Manager() {
    }

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    public double getBonus() {
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
