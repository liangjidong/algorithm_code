package com.ljd.javabase;

import java.math.BigInteger;

public class JavaBaseTest {
    public static final StaticObj STATIC_OBJ = new StaticObj();

    // 创建Exception的时候，会调用Throwable.fillInStackTrace()方法把stacktrace填充好，
    public static Exception e = new Exception();

    public static void main(String[] args) {
        System.out.println(STATIC_OBJ);
        STATIC_OBJ.setA(10);
        System.out.println(STATIC_OBJ);
        System.out.println("string test");
        stringAndCharTest();
        System.out.println("try-catch-finally-test");
        tryCatchFinallyTest();
    }

    public static void testBigInteger() {
        BigInteger bi = new BigInteger("11111111111111", 10);
    }

    public static void stringAndCharTest() {
//        String s = "한가인zxc";
//        System.out.println(s.length());
//        char c = '한';
//        System.out.println(c);

        final String str1 = "str";
        final String str2 = "ing";
        // 下面两个表达式其实是等价的
        String c = "str" + "ing";// 常量池中的对象
        String d = str1 + str2; // 常量池中的对象
        System.out.println(c == d);// true
    }

    public static void tryCatchFinallyTest() {
        try {
            System.out.println(tryCatchFinallyReturn(null));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            System.out.println(tryCatchFinallyReturn(null));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int tryCatchFinallyReturn(Object o) throws Exception {
        System.out.println("tryCatchFinallyReturn execute");
        boolean b = soutAndReturn(o);
        return b ? 1 : 0;
    }

    private static boolean soutAndReturn(Object o) throws Exception {
        System.out.println("sout execute");
        if (o == null) {
            throw new Exception();
        }
        return true;
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
