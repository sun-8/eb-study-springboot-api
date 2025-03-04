package com.study.api.service;

import com.study.api.mapper.BoardMapper;
import com.study.api.model.out.board.BoardListDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import com.study.api.model.out.category.CategoryListDTO;
import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.out.BoardSearchOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class BoardService {
    Logger logger = Logger.getLogger(BoardService.class.getName());

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BoardMapper boardMapper;

    /**
     * 게시판 목록 조회
     * @param inDTO
     * @return outDTO
     */
    public BoardSearchOutDTO boardSearch(BoardSearchInDTO inDTO) {

        BoardSearchOutDTO outDTO = new BoardSearchOutDTO();

        /* todo.
            카테고리 목록은 따로 조회하는 것이 좋았을까?
        * */
        // 카테고리 목록
        List<CategoryListDTO> categoryList = categoryService.getCategoryAllList();
        outDTO.setCategoryList(categoryList);

        /* todo.
            아래 세 가지 DTO로 분리
            inDTO : 데이터 요청 모음
            processDTO : 데이터 처리를 위한 변수 모음
            outDTO : 데이터 응답 모음
        * */
        // 데이터 처리 셋팅
        BoardSearchProcessDTO boardSearchProcessDTO = new BoardSearchProcessDTO();
        int pageSize = 10;
        boardSearchProcessDTO.setSearchRegisterDateStart(inDTO.getSearchRegisterDateStart());
        boardSearchProcessDTO.setSearchRegisterDateEnd(inDTO.getSearchRegisterDateEnd());
        boardSearchProcessDTO.setSearchCategory(inDTO.getSearchCategory());
        boardSearchProcessDTO.setSearchWord(inDTO.getSearchWord());
        boardSearchProcessDTO.setOffsetByNowPageAndPageSize(inDTO.getNowPage(), pageSize);
        boardSearchProcessDTO.setPageSize(pageSize);

        // 페이지 계산
        outDTO.setNowPage(inDTO.getNowPage());
        int searchDataCount = this.getBoardSearchCount(boardSearchProcessDTO);
        outDTO.setSearchDataCount(searchDataCount);
        outDTO.setLastPageBySearchDataCountAndPageSize(searchDataCount, pageSize);

        // 게시판 목록
        List<BoardListDTO> boardList = this.getBoardSearchList(boardSearchProcessDTO);
        outDTO.setBoardList(boardList);

        return outDTO;
    }

    /**
     * 게시글 총 개수 (검색조건)
     * @param boardSearchProcessDTO
     * @return cnt
     */
    public int getBoardSearchCount(BoardSearchProcessDTO boardSearchProcessDTO) {

        int cnt = boardMapper.getBoardSearchCount(boardSearchProcessDTO);

        return cnt;
    }

    /**
     * 게시판 목록 조회 (검색조건, 페이징)
     * @param boardSearchProcessDTO
     * @return boardList
     */
    public List<BoardListDTO> getBoardSearchList(BoardSearchProcessDTO boardSearchProcessDTO) {

        List<BoardListDTO> boardList = boardMapper.getBoardSearchList(boardSearchProcessDTO);

        return boardList;
    }
}
