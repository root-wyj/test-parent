package com.wyj.agent.agent1;

import java.lang.instrument.Instrumentation;

/**
 * @author wuyingjie
 * Date: 2022/1/19
 */
public class TestAgent1 {

    public static void premain(String agentArgs, Instrumentation _inst) {
        System.out.println("TestAgent1.premain was called. args:" + agentArgs);
        _inst.addTransformer(new Agent1ClassFileTransformer(), true);
    }

}
