package com.juc.single;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 枚举实现饿汉式单例模式
 * 最简单的实现方式，基于枚举类型的单例实现。
 * 这种实现方式通过 Java 枚举类型本身的特性，
 * 保证了实例创建的线程安全性和实例的唯一性。
 * (1)自由序列化。
 * (2)保证只有一个实例。
 * (3)线程安全。
 * 单例模式应用的场景一般发现在以下条件下：
 *  （1）资源共享的情况下，避免由于资源操作时导致的性能或损耗等。如上述中的日志文件，应用配置。
 *  （2）控制资源的情况下，方便资源之间的互相通信。如线程池等。
 */
public enum EnumSingle {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);
    public long getId() {
        return id.incrementAndGet();
    }
    public void otherMethods(){
        System.out.println("Others");
    }

    public static void main(String[] args) {
        System.out.println(EnumSingle.INSTANCE.getId());
    }
}
