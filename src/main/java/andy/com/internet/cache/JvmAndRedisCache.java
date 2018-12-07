package andy.com.internet.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 本地缓存(jvm)+redis 缓存 使用方法
 */
public class JvmAndRedisCache {


    private static Logger logger = LoggerFactory.getLogger("");
    private static Gson gson = new Gson();

    private MultiLayerCacheReadTemplate readTemplate = new MultiLayerCacheReadTemplate();

    private final String nullFlagWord = new String();

    private Cache<Object, String> localCacheWord = CacheBuilder.newBuilder().maximumSize(20000)
            .concurrencyLevel(32).expireAfterWrite(15l, TimeUnit.MINUTES).build();

    private Function<List<String>, Map<String, String>> wordReader = new Function<List<String>, Map<String, String>>() {

        @Override
        public Map<String, String> apply(List<String> words) {
            return words.stream().collect(Collectors.toMap(t->t,t->t+"11"));
        }
    };

    /**
     * 这个方法就是具体的多层cache
     * @param words
     * @return
     */
    public Map<String, String> getWords(List<String> words) {

        if (CollectionUtils.isEmpty(words)) {
            return Collections.EMPTY_MAP;
        }

        return readTemplate.read(localCacheWord, y -> y, y->y, wordReader,
                words, nullFlagWord, logger, 60 * 60 * 24, x -> gson.fromJson(x, String.class), x -> gson.toJson(x));
    }


    public static void main(String [] args){
        new JvmAndRedisCache().getWords(Arrays.asList(""));
    }
}
