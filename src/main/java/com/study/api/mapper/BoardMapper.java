package com.study.api.mapper;

import com.study.api.model.out.board.BoardListDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    /**
     * 게시글 총 개수 (검색조건)
     * @param boardSearchProcessDTO
     * @return int
     */
    int getBoardSearchCount(BoardSearchProcessDTO boardSearchProcessDTO);

    /**
     * 게시판 목록 조회 (검색조건, 페이징)
     * @param boardSearchProcessDTO
     * @return List<BoardListDTO>
     */
    List<BoardListDTO> getBoardSearchList(BoardSearchProcessDTO boardSearchProcessDTO);

}
