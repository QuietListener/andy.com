package andy.com.db.redis.ratelimiting;

/**
 * 限流 令牌桶算法
 */
public class TokenBucket {

    /**
     * 使用 redis做一个分布式的令牌桶
     * @param bucketName
     * @return
     */
    public boolean acquire(String bucketName){

        return false;
    }

    /**
     *
     * @param script
     * @param bucketName 桶的名字
     * @param countPerSecond 每秒钟向桶里放入的令牌数量
     * @param bucketCapacity 桶的容量
     */
    private boolean executeScript(String script, String bucketName, long countPerSecond,long bucketCapacity) {

        return true;
    }




}
