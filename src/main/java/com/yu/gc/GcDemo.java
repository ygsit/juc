package com.yu.gc;

public class GcDemo {
    public static void main(String[] args) {
        System.out.println("hello gc");
        try { Thread.sleep(Integer.MAX_VALUE); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
