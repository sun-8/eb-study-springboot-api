package com.study.api.model.mapstruct;

import com.study.api.model.in.CommentFormInsertInDTO;
import com.study.api.model.process.CommentInfoProcessDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapStruct {
    CommentMapStruct INSTANCE = Mappers.getMapper(CommentMapStruct.class);

    // CommentFromInsertInDTO -> CommentInfoProcessDTO
    @Mapping(target="registerDatetime", ignore = true)
    CommentInfoProcessDTO commentFromInsertInDtoToCommentInfoProcessDto(CommentFormInsertInDTO commentFormInsertInDTO);
}
