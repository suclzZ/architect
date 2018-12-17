package com.sucl.agent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * 将该项目打成jar包(通过maven shade或assembly)
 * 需要在META-INF/manifest.mf中加入Premain-Class:com.sucl.agent.AcAgent指向该类
 * 在执行com.basic.bytecode.HelloWorld.hello时，需要加上jvm参数-javaagent:xxx.jar=option
 */
public class AcAgent implements ClassFileTransformer {
    private static String injectClassName = "com.basic.bytecode.HelloWorld";
    private static String methodName = "hello";

    public static void premain(String option , Instrumentation inst){
        System.out.println("run premain");
        inst.addTransformer(new AcAgent());
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replaceAll("/",".");
        if(className.equals(injectClassName)){
            ClassPool classPool = new ClassPool();
            try {
                classPool.insertClassPath(new LoaderClassPath(loader));
                CtClass ctClass = classPool.get(className);
                CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);
                ctMethod.insertBefore("System.out.println(\"...before...\");");
                return ctClass.toBytecode();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (CannotCompileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
