package com.bsa.bsagiphy.repository.impl;

import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.repository.GifRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CacheMemoryRepository implements GifRepository {

    private static final Map<String, List<Cache>> cache = new HashMap<>();

    public void updateCache(String userId, String query, Gif gif) {
        var userCache = cache.getOrDefault(userId, new ArrayList<>());
        Optional<Cache> maybeCache = userCache.stream().filter(c -> Objects.equals(c.getQuery(), query)).findFirst();
        if (maybeCache.isPresent()) {
            var oldCache = maybeCache.get();
            var gifs = oldCache.getGifs();
            gifs.add(gif.getPath());
            var newCache = new Cache(query, gifs);
            userCache.replaceAll(c -> {
                if (Objects.equals(c.getQuery(), query)) {
                    return newCache;
                }
                return c;
            });
            cache.put(userId, userCache);
        } else {
            var gifs = new ArrayList<String>();
            gifs.add(gif.getPath());
            userCache.add(new Cache(query, gifs));
        }
    }
}
