package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.*;
import com.bsa.bsagiphy.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class ApiUserController {

    private final UserOperationService userOperationService;

    @Autowired
    public ApiUserController(UserOperationService userOperationService) {
        this.userOperationService = userOperationService;
    }

    @GetMapping("/{id}/all")
    public List<CacheDto> getAllFiles(@PathVariable String id) {
        // todo
        return null;
    }

    @GetMapping("/{id}/history")
    public UserHistoryDto getHistory(@PathVariable String id) {
        // todo
        return null;
    }

    @DeleteMapping("/{id}/history/clean")
    public void cleanHistory(@PathVariable String id) {
        // todo
    }

    @GetMapping("/{id}search/")
    public GifResponseDto searchGif(@PathVariable String id, @RequestParam Map<String, String> params) {
        // todo
        return null;
    }

    @PostMapping("/{id}/generate")
    public GifResponseDto createGifForUser(@PathVariable String id, @RequestBody GenerateGifForUserDto generateGifForUserDto) {
        // todo
        return null;
    }

    @DeleteMapping("/{id}/reset")
    public void resetUserCache(@PathVariable String id, @RequestParam String query) {
        // todo
    }

    @DeleteMapping("/{id}/clean")
    public void cleanAllUserData(@PathVariable String id) {
        // todo
    }

}
