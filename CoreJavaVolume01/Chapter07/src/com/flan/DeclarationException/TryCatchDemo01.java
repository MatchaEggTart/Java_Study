package com.flan.DeclarationException;

public class TryCatchDemo01 {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5, 6};

        try {
            for (int i = arr.length - 1; ; i--) {
                System.out.println(arr[i]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("执行catch语句！");
            // e.printStackTrace();
        }
    }
}
