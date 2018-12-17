package com.basic.bytecode;

/**
 * 运行前加了参数-javaagent:F:\sucl\architect\java-agent\target\java-agent-1.0-SNAPSHOT.jar
 */
public class HelloWorld {

    public static void main(String[] args) {
//        new HelloWorld().hello();
        hello();
    }

    public static void hello(){
        System.out.println("Hello World!");
    }
}
