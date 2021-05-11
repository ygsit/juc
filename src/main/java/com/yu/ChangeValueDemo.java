package com.yu;

public class ChangeValueDemo {

    public void changeIntValue(int i){
        i = 30;
    }

    public void changeStringValue(String s){
        s = "xxx";
    }

    public static void main(String[] args) {
        ChangeValueDemo changeValueDemo = new ChangeValueDemo();
        //八大基本类型传的是复印件
        int a = 20;
        changeValueDemo.changeIntValue(20);
        System.out.println("a == " + a);

        //引用类型String的值不会变化，其他引用类型会
        String s = "abc";
        changeValueDemo.changeStringValue(s);
        System.out.println("s == " + s);

    }

}
