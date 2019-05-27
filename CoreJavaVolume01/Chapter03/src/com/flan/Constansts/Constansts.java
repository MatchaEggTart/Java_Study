package com.flan.Constansts;

public class Constansts {
    public static void main(String[] args) {
        final double CM_PER_INCH ;
        CM_PER_INCH= 2.54;
        double paperWidth = 8.5;
        double paperHeight = 11;
        // CM_PER_INCH = 3;     can't do this, error
        System.out.println("Paper size in centimeters: "
        + paperWidth * CM_PER_INCH + " by " + paperHeight * CM_PER_INCH);
    }
}
