package com.hqs.rediscache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheApplicationTests {


	public void contextLoads() {
	}

	public static void main(String[] args) {
		Config config=new Config();
		config.useSingleServer().setAddress("192.168.0.104:6379");
		Redisson redisson = (Redisson) Redisson.create(config);
		DistributedRedisLock distributedRedisLock = new DistributedRedisLock(redisson);
		distributedRedisLock.acquire("taibai");
		distributedRedisLock.release("taibai");
	}

	public static class DistributedRedisLock {
		private Redisson redisson;
		public DistributedRedisLock(Redisson redisson){
			this.redisson=redisson;
		}

		//从配置类中获取redisson对象
		private static final String LOCK_TITLE = "redisLock_";
		//加锁
		public  boolean acquire(String lockName){
			//声明key对象
			String key = LOCK_TITLE + lockName;
			//获取锁对象
			RLock mylock = redisson.getLock(key);
			//加锁，并且设置锁过期时间，防止死锁的产生
			mylock.lock(2, TimeUnit.MINUTES);
			System.err.println("======lock======"+Thread.currentThread().getName());
			//加锁成功
			return  true;
		}
		//锁的释放
		public  void release(String lockName){
			//必须是和加锁时的同一个key
			String key = LOCK_TITLE + lockName;
			//获取所对象
			RLock mylock = redisson.getLock(key);
			//释放锁（解锁）
			mylock.unlock();
			System.err.println("======unlock======"+Thread.currentThread().getName());
		}
	}

}
