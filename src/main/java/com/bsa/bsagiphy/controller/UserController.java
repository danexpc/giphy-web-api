package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.dto.UserHistoryDto;
import com.bsa.bsagiphy.mapper.UserCacheMapper;
import com.bsa.bsagiphy.service.impl.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserOperationService service;
    private final UserCacheMapper mapper;

    @Autowired
    public UserController(UserOperationService service, @Qualifier("userMapper") UserCacheMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}/all")
    public List<CacheDto> getAllFiles(@PathVariable String id) {
        return mapper.listCacheToListCacheDto(service.getAllGifsById(id));
    }

    @GetMapping("/{id}/history")
    public List<UserHistoryDto> getHistory(@PathVariable String id) {
        return mapper.listUserHistoryToListUserHistoryDto(service.getHistoryById(id));
    }

    @DeleteMapping("/{id}/history/clean")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cleanHistory(@PathVariable String id) {
        service.deleteHistoryById(id);
    }

    @GetMapping("/{id}/search")
    public String searchGif(@PathVariable String id,
                            @RequestParam String query,
                            @RequestParam(defaultValue = "false") boolean force) {
        try {
            return force ? service.getGifInStorageByQuery(id, query).getPath()
                    : service.getGifInCacheByQuery(id, query).getPath();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }

    }

    @PostMapping("/{id}/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public String createGifForUser(@PathVariable String id, @RequestBody GenerateGifForUserDto dto) {
        return service.createGif(id, dto);
    }

    @DeleteMapping("/{id}/reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetUserCache(@PathVariable String id, @RequestParam Optional<String> query) {
        if (query.isPresent()) {
            service.resetCacheByQuery(id, query.get());
        } else {
            service.resetAllCache(id);
        }
    }

    @DeleteMapping("/{id}/clean")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cleanAllUserData(@PathVariable String id) {
        service.deleteAllData(id);
    }

}
