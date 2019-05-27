package com.flan.DeclarationException;

public class DeclarationException  {
    public static void main(String[] args) {
        Integer[] arr = new Integer[]{1,2,3,4,5};
        printarr(arr, 1);
    }

    private static <T> void printarr(T[] a, int b) throws ArrayIndexOutOfBoundsException{
        if (b < 2) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException();
            throw e;
        }
        System.out.println(a[b]);
    }
}
