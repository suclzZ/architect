package com.basic.link;

import java.util.Arrays;

public class Linked {
    public Linked(String value ,Linked next){
        this.value = value;
        this.next = next;
    }

    private String value;
    private Linked next;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Linked getNext() {
        return next;
    }

    public void setNext(Linked next) {
        this.next = next;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder(this.value);
        Linked next = this.next;
        while (next!=null){
            sb.append( "->" +next.value );
            next = next.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Linked[] ls = new Linked[5];

        Linked l1 = new Linked("1",null);
        Linked l2 = new Linked("2",l1);
        Linked l3 = new Linked("3",l2);
        Linked l4 = new Linked("4",l3);
        Linked l5 = new Linked("5",l4);
        ls[0] = l1;ls[1] = l2;ls[2] = l3;ls[3] = l4;ls[4] = l5;
        //5 -> 4 -> 3 -> 2-> 1
  //      System.out.println(Arrays.toString(ls));
        System.out.println(l5);

        Linked link = reverse(l5);

        System.out.println(link);

    }

    /**
     * HashMap中的一段源码transfer,jdk8 resize
     * 单向链表翻转
     * @param link
     * @return
     */
    public static Linked reverse(Linked link){
        Linked tmp = null;
        while (link!=null){
            Linked next = link.next;
            link.next = tmp;
            tmp = link;
            link = next;
        }
        return tmp;
    }

}

