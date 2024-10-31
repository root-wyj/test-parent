package com.wyj.test.loadclass;

public class TestSingleton {

    /**
     * 输出是什么
     * counter1=1
     * counter2=0
     *
     * 1 执行 TestSingleton 第一句的时候，因为我们没有对 Singleton 类进行加载和连接，所以我们首先需要对它进行加载和连接操作。在连接阶 - 准备阶段，我们要讲给静态变量赋予默认初始值。
     * singleton =null
     * counter1 =0
     * counter2 =0
     * 2 加载和连接完毕之后，我们再进行初始化工作。初始化工作是从上往下依次执行的，注意这个时候还没有调用 Singleton.getSingleton();
     * 首先 singleton = new Singleton(); 这样会执行构造方法内部逻辑，进行 ++；此时 counter1=1，counter2 =1 ；
     * 接下来再看第二个静态属性，我们并没有对它进行初始化，所以它就没办法进行初始化工作了；
     * 第三个属性 counter2 我们初始化为 0 , 而在初始化之前 counter2=1，执行完 counter2=0 之后 counter2=0 了；
     *
     * 3 初始化完毕之后我们就要调用静态方法 Singleton.getSingleton(); 我们知道返回的 singleton 已经初始化了。
     * 那么输出的内容也就理所当然的是 1 和 0 了。
     */
    public static void main(String args[]){
        Singleton singleton = Singleton.getSingleton();
        System.out.println("counter1="+singleton.counter1);
        System.out.println("counter2="+singleton.counter2);

    }
}