package com.bsa.bsagiphy.mapper;

import com.bsa.bsagiphy.dto.UserHistoryDto;
import com.bsa.bsagiphy.entity.UserHistory;
import org.mapstruct.Mapper;

@Mapper
public interface UserHistoryMapper {

    UserHistoryDto UserHistoryToUserHistoryDto(UserHistory entity);

    UserHistory UserHistoryDtoToUserHistory(UserHistoryDto dto);
}
