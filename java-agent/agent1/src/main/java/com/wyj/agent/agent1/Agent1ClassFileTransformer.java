package com.wyj.agent.agent1;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author wuyingjie
 * Date: 2022/1/19
 */
public class Agent1ClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (className == null || !className.startsWith("com/wyj/agent/test")) {
            return classfileBuffer;
        }
        System.out.println("Agent1ClassFileTransformer start transform class:" + className);
        byte[] transformed = classfileBuffer;
        CtClass cl = null;
        try {
            ClassPool classPool = ClassPool.getDefault();
            cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            if (cl.isInterface()) {
                System.out.println("Agent1ClassFileTransformer transform class:" + className + " skip. is interface");
                return transformed;
            }
            // 每个方法都插入自己的逻辑
            CtBehavior[] methods = cl.getDeclaredBehaviors();
            for (CtBehavior method : methods) {
                if (!method.isEmpty()) {
                    doTransformMethod(method);
                }
            }
            transformed = cl.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }

        return transformed;
    }

    private void doTransformMethod(CtBehavior method) throws CannotCompileException {
//        method.insertBefore("long sTime = System.nanoTime();");
//        method.insertAfter("System.out.println(\"leave "+method.getName()+" and time:\"+(System.nanoTime()-sTime));");
        if (method.getLongName().startsWith("com.wyj.agent.test")) {
            System.out.println("transformed method:" + method.getLongName());
            method.instrument(new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    m.replace("{ long stime = System.nanoTime(); $_ = $proceed($$); System.err.println(\""
                            + m.getClassName() + "." + m.getMethodName()
                            + ":\"+(System.nanoTime()-stime));}");
                }
            });
        }
    }
}
