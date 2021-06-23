package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.UserHistoryDto;
import com.bsa.bsagiphy.entity.UserHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserHistoryMapper {

    UserHistoryDto userHistoryToUserHistoryDto(UserHistory entity);

    List<UserHistoryDto> listUserHistoryToListUserHistoryDto(List<UserHistory> entities);

    UserHistory userHistoryDtoToUserHistory(UserHistoryDto dto);
}
