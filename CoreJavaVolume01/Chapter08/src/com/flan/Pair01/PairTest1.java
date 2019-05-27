package com.flan.Pair01;

public class PairTest1 {

    public static void main(String[] args) {
        String[] words = {"Micheal", "Tommy", "Roy", "Adrien"};

        Pair<String> mm = ArrayAlg.minmax(words);

        System.out.println("The Max one is : " + mm.getSecond());
        System.out.println("The Min one is : " + mm.getFirst());
    }

}

class ArrayAlg {
    public static Pair<String> minmax(String[] a) {
        // 检查传入数组是不是 没指向 或者 空数组
        if (a == null || a.length == 0) return null;

        String max = a[0];
        String min = a[0];

        for (int i = 1; i < a.length; i++) {
            if (max.length() < a[i].length()) max = a[i];
            if (min.length() > a[i].length()) min = a[i];
        }

        // 这个new很关键！
        return new Pair<>(min, max);
    }
}
