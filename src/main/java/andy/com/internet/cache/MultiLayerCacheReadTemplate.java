package andy.com.internet.cache;

import com.google.common.cache.Cache;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 多层缓存
 */
public class MultiLayerCacheReadTemplate {

    @Autowired
    private JedisManager jedisManager;

    public <Q,R> Map<Q,R> read(Cache<Object,R> localCache, Function<Q,Object> localCacheKeyBuilder, Function<Q,String> remoteCacheKeyBuilder,
                               Function<List<Q>, Map<Q,R>> cacheMissReader,
                               List<Q> queryArgs, R nullFLag, Logger logger, int remoteCacheExpire, Function<String,R> fromJson, Function<R,String> toJson) {

        List<Q> localMiss = new LinkedList<Q>();
        Map<Q,R> ret = new HashMap<Q, R>();
        if (localCache != null && localCacheKeyBuilder != null) {
            Map<Q, Object> localKeys = queryArgs.stream()
                    .collect(Collectors.toMap(x -> x, x -> localCacheKeyBuilder.apply(x)));
            for (Q q : queryArgs) {
                Object lk = localKeys.get(q);
                R l = localCache.getIfPresent(lk);
                if (l == null) {
                    localMiss.add(q);
                } else {
                    ret.put(q, l);
                }
            }
        } else {
            localMiss = queryArgs;
        }

        if (!CollectionUtils.isEmpty(localMiss)) {
            List<Q> remoteMiss = new LinkedList<Q>();
            try {
                List<String> remoteKeys = localMiss.stream().map(x -> remoteCacheKeyBuilder.apply(x))
                        .collect(Collectors.toList());
                List<String> jsons = jedisManager.batchGet(remoteKeys);
                int i = 0;
                for (Q q : localMiss) {
                    String json = jsons.get(i);
                    if (!StringUtils.isEmpty(json)) {
                        ret.put(q, fromJson.apply(json));
                    } else {
                        remoteMiss.add(q);
                    }
                    i++;
                }
            } catch (Exception e) {
                remoteMiss.clear();
                remoteMiss.addAll(localMiss);
                logger.warn("read redis error:" + e.getMessage(),e);
            }

            if (!CollectionUtils.isEmpty(remoteMiss)) {
                ret.putAll(cacheMissReader.apply(remoteMiss));
                ArrayList<String> keys = new ArrayList<>(remoteMiss.size());
                ArrayList<String> values = new ArrayList<>(remoteMiss.size());
                for (Q q : remoteMiss) {
                    if (ret.get(q) != null) {
                        keys.add(remoteCacheKeyBuilder.apply(q));
                        values.add(toJson.apply(ret.get(q)));
                    }
                }
                if (!CollectionUtils.isEmpty(keys) && !CollectionUtils.isEmpty(values)) {
                    try {
                        jedisManager.batchWriteCache(keys, values, remoteCacheExpire);
                    } catch (Exception e) {
                        logger.warn("write redis error:" + e.getMessage(), e);
                    }
                }
            }

            if (localCache != null && localCacheKeyBuilder != null) {
                for (Q q : localMiss) {
                    if (ret.get(q) != null) {
                        localCache.put(localCacheKeyBuilder.apply(q), ret.get(q));
                    } else if (nullFLag != null) {
                        localCache.put(localCacheKeyBuilder.apply(q),nullFLag);
                    }
                }
            }

        }
        if (nullFLag != null) {
            Iterator<Map.Entry<Q,R>> it = ret.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Q,R> e = it.next();
                if (e.getValue() == nullFLag) {
                    it.remove();
                }
            }
        }
        return ret;
    }

}
