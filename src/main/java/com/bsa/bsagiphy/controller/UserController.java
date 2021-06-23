package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.dto.UserHistoryDto;
import com.bsa.bsagiphy.service.impl.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserOperationService userOperationService;

    @Autowired
    public UserController(UserOperationService userOperationService) {
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
    public GifResponseDto searchGif(@PathVariable String id,
                                    @RequestParam(required = false) String query,
                                    @RequestParam(defaultValue = "false") Boolean force) {
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
