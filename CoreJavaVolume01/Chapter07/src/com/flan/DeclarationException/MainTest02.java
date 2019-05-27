package com.flan.DeclarationException;

/**
 * Exception in thread "main" com.flan.DeclarationException.ArrayIndexLow: 不能小于2
 * 	at com.flan.DeclarationException.MainTest02.printarr(MainTest02.java:11)
 * 	at com.flan.DeclarationException.MainTest02.main(MainTest02.java:6)
 */
public class MainTest02 {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1,2,3,4,5};
        printarr(arr, 1);
    }

    private static <T> void printarr(T[] arr, int b)  throws ArrayIndexLow {
        if (b < 2) {
            ArrayIndexLow e = new ArrayIndexLow("不能小于2");
            throw e;
        }

        System.out.println(arr[b]);
    }
}
