package com.basic.beans;

public class Tom extends Cat{

    private String no;
    private int age;

    private void name(){
        String name = "tom";
        System.out.println(name);
    }

    public Tom(String no,int age){
        this.no = no;
        this.age = age;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
