package com.flan.DeclarationException;

public class ArrayIndexLow extends ArrayIndexOutOfBoundsException{
    public ArrayIndexLow() {
        System.out.println("太少了");
    }

    public ArrayIndexLow(String gripe) {
        super(gripe);
    }
}
