package com.bsa.bsagiphy.repository;

import com.bsa.bsagiphy.entity.Cache;
import com.bsa.bsagiphy.entity.Gif;
import com.bsa.bsagiphy.repository.GifRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Repository
public class CacheMemoryRepository implements GifRepository {

    private static final Map<String, List<Cache>> cache = new HashMap<>();

    public Optional<Gif> getGifByQuery(String userId, String query) {
        var userCache = cache.get(userId);
        if (userCache == null)
            return Optional.empty();

        var maybeCache = userCache.stream().filter(c -> Objects.equals(c.getQuery(), query)).findFirst();
        if (maybeCache.isEmpty())
            return Optional.empty();

        var gifs = maybeCache.get().getGifs();
        if (gifs.isEmpty())
            return Optional.empty();

        var gif = new Gif();
        gif.setPath(gifs.get(new Random().nextInt(gifs.size())));
        return Optional.of(gif);
    }

    public void updateCache(String userId, String query, Gif gif) {
        log.error(cache);
        var userCache = cache.getOrDefault(userId, new ArrayList<>());
        var maybeCache = userCache.stream().filter(c -> Objects.equals(c.getQuery(), query)).findFirst();
        if (maybeCache.isPresent()) {
            var oldCache = maybeCache.get();
            var gifs = oldCache.getGifs();
            if (!gifs.contains(gif.getPath())) {
                gifs.add(gif.getPath());
                var newCache = new Cache(query, gifs);
                userCache.replaceAll(c -> {
                    if (Objects.equals(c.getQuery(), query)) {
                        return newCache;
                    }
                    return c;
                });
                cache.put(userId, userCache);
            }
        } else {
            var gifs = new ArrayList<String>();
            gifs.add(gif.getPath());
            userCache.add(new Cache(query, gifs));
            cache.put(userId, userCache);
        }
    }

    public void deleteCache(String id) {
        log.error("Before " + cache);
        cache.put(id, new ArrayList<>());
        log.error("After " + cache);
    }

    public void deleteCacheByQuery(String id, String query) {
        log.error("Before " + cache);
        var userCache = cache.getOrDefault(id, new ArrayList<>());
        if (userCache.isEmpty()) {
            return;
        }

        var filteredCache = userCache.stream()
                .filter(c -> !c.getQuery().equals(query)).collect(Collectors.toList());

        cache.put(id, filteredCache);
        log.error("After " + cache);
    }
}
