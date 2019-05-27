package com.flan.DeclarationException;

public class FinallyDemo {
    public static void main(String[] args) {

        Integer[] arr = new Integer[]{1,2,3,4,5,6};

        prinarr(arr, 2);
        prinarr(arr, -2);
        prinarr(arr, 0);
    }

    private static <T> void prinarr(T[] a, int b) {
        try {
            System.out.println(a[b]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("不能少于0啊, 哥");
        } finally {
            System.out.println("执行了finally");
            System.out.println("---------------------");
            System.out.println();
        }
    }
}
