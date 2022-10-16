package com.ljd.javabase;

import java.math.BigInteger;

public class JavaBaseTest {
    public static final StaticObj STATIC_OBJ = new StaticObj();

    public static void main(String[] args) {
        System.out.println(STATIC_OBJ);
        STATIC_OBJ.setA(10);
        System.out.println(STATIC_OBJ);
    }

    public static void testBigInteger() {
        BigInteger bi = new BigInteger("11111111111111", 10);
    }
}

class StaticObj {
    public int a;

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "StaticObj:" + a;
    }
}
