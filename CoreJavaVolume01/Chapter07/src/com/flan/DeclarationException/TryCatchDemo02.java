package com.flan.DeclarationException;

public class TryCatchDemo02 {
    public static void main(String[] args) {

        Integer[] arr = new Integer[] {1,2,3,4,5,6};
        try {
            for (int i = arr.length - 1; ; i--) {
                printarr(arr, i);
            }
        } catch (ArrayIndexLow e) {
            System.out.println("不能小于1");
            e.printStackTrace();
        }
    }

    private static <T> void printarr(T[] arr, int b) throws ArrayIndexLow {

        if (b < 1) {
            ArrayIndexLow e = new ArrayIndexLow();
            throw e;
        }
        System.out.println(arr[b]);
    }
}
