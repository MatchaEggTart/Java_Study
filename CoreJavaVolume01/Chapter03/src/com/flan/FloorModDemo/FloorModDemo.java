package com.flan.FloorModDemo;

import static java.lang.Math.floorMod;

public class FloorModDemo {
    public static void main(String[] args) {
        int position = -13;
        int adjustment = 12;

        double x = 1000F + 2;
        System.out.println((position + adjustment) % 12);
        System.out.println(floorMod(position + adjustment, 12));
        System.out.println(x);
    }
}
