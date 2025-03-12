package com.study.api.model.mapstruct;

import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapStruct {
    BoardMapStruct INSTANCE = Mappers.getMapper(BoardMapStruct.class);

    // BoardSearchInDTO -> BoardSearchProcessDTO 변환
    @Mapping(target="offset", ignore = true)
    @Mapping(target = "searchDataCount", ignore = true)
    @Mapping(target="lastPage", ignore = true)
    BoardSearchProcessDTO boardSearchInDtoToBoardSearchProcessDto(BoardSearchInDTO boardSearchInDTO, int pageSize);

    // offset 설정 메서드
    default void setOffsetByNowPage(BoardSearchInDTO boardSearchInDTO, @MappingTarget BoardSearchProcessDTO boardSearchProcessDTO) {
        int nowPage = boardSearchInDTO.nowPage();
        boardSearchProcessDTO.setOffsetByNowPage(nowPage);
    }

    // searchDataCount 설정 메서드
    default void setSearchDataCount(int searchDataCount, @MappingTarget BoardSearchProcessDTO boardSearchProcessDTO) {
        boardSearchProcessDTO.setSearchDataCount(searchDataCount);
    }

    // lastPage 설정 메서드
    default void setLastPageBySearchDataCount(int searchDataCount, @MappingTarget BoardSearchProcessDTO boardSearchProcessDTO) {
        boardSearchProcessDTO.setLastPageBySearchDataCount(searchDataCount);
    }
}
