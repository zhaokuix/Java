package utils;

import com.crux.common.exception.InnerException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;


public class RedissonLockUtil {

    protected final Logger logger = LoggerFactory.getLogger(RedissonLockUtil.class);

    private RedissonClient redissonClient;

    private RedissonLockUtil(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 获取RedissonLockUtil对象静态工厂方法
     *
     * @param redissonClient 创建对象是需注入redissonClient对象依赖
     * @return RedissonLockUtil对象
     */
    public static RedissonLockUtil newInstance(RedissonClient redissonClient) {
        return new RedissonLockUtil(redissonClient);
    }

    /**
     * redisson分布式锁执行器
     *
     * @param supplier 需加锁执行内容提供者
     * @param lockKey  锁键
     * @param <T>      执行内容返回类型
     * @return 执行内容返回值
     * @throws Exception 执行过程异常
     */
    public <T> T redissonLockExecute(Supplier<T> supplier, String lockKey) {
        RLock rLock = null;
        T t;
        try {
            rLock = this.redissonClient.getLock(lockKey);
            if (rLock.tryLock()) {
                t = supplier.get();
            } else {
                // 等待3ms重试
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new InnerException("获取redis锁超时");
                }
                Long time1 = System.currentTimeMillis();
                boolean isLock = rLock.tryLock();
                while (!isLock) {
                    Long time2 = System.currentTimeMillis();
                    //如果获取锁时间超过5000ms则放弃获取锁，提示获取锁超时
                    if (time2 - time1 > 5000) {
                        throw new InnerException("获取redis锁超时!");
                    }
                    //每隔3ms重新获取一次
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        throw new InnerException("获取redis锁超时");
                    }
                    isLock = rLock.tryLock();
                }
                t = supplier.get();
            }
        } finally {
            //释放锁
            if (rLock != null) {
                rLock.unlock();
            }
        }
        return t;
    }
}
