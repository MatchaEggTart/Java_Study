package com.flan.DeclarationException;

public class MainTest01 {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5};
        printarr(arr, 1);                // 这里设定了 <2 也会跳出异常, 实际没有异常， 只是为了演示异常
    }

    private static <T> void printarr(T[] a, int b) throws ArrayIndexOutOfBoundsException {
        if (b < 2) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException();
            throw e;
        }
        System.out.println(a[b]);
    }
}
