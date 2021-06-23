package com.bsa.bsagiphy.controller;

import com.bsa.bsagiphy.dto.CacheDto;
import com.bsa.bsagiphy.dto.GenerateGifForUserDto;
import com.bsa.bsagiphy.dto.GifResponseDto;
import com.bsa.bsagiphy.dto.UserHistoryDto;
import com.bsa.bsagiphy.mapper.UserCacheMapper;
import com.bsa.bsagiphy.service.impl.UserOperationService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserOperationService service;
    private final UserCacheMapper mapper = Mappers.getMapper(UserCacheMapper.class);

    @Autowired
    public UserController(UserOperationService service) {
        this.service = service;
    }

    @GetMapping("/{id}/all")
    public List<CacheDto> getAllFiles(@PathVariable String id) {
        return mapper.listCacheToListCacheDto(service.getAllPersonalFilesByUserId(id));
    }

    @GetMapping("/{id}/history")
    public UserHistoryDto getHistory(@PathVariable String id) {
        return mapper.userHistoryToUserHistoryDto(service.getHistoryByUserId(id));
    }

    @DeleteMapping("/{id}/history/clean")
    public void cleanHistory(@PathVariable String id) {
        service.cleanHistoryByUserId(id);
    }

    @GetMapping("/{id}/search/")
    public GifResponseDto searchGif(@PathVariable String id,
                                    @RequestParam String query,
                                    @RequestParam(defaultValue = "false") Boolean force) {
        return mapper.gifToGifResponseDto(service.searchGifByQuery(id, query, force));
    }

    @PostMapping("/{id}/generate")
    public GifResponseDto createGifForUser(@PathVariable String id, @RequestBody GenerateGifForUserDto dto) {
        return mapper.gifToGifResponseDto(service.createGif(id, dto));
    }

    @DeleteMapping("/{id}/reset")
    public void resetUserCache(@PathVariable String id, @RequestParam Optional<String> query) {
        if (query.isPresent()) {
            service.resetUserCacheByQuery(id, query.get());
        } else {
            service.resetAllUserCache(id);
        }
    }

    @DeleteMapping("/{id}/clean")
    public void cleanAllUserData(@PathVariable String id) {
        service.cleanAllUserData(id);
    }

}
