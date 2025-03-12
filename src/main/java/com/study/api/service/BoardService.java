package com.study.api.service;

import com.study.api.mapper.BoardMapper;
import com.study.api.model.error.BoardFormInsertErrorDTO;
import com.study.api.model.in.BoardFormInsertInDTO;
import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.CategoryListOutDTO;
import com.study.api.model.out.board.BoardListDTO;
import com.study.api.model.process.BoardInfoProcessDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import com.study.api.model.process.MultiFileProcessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BoardService {
    Logger logger = Logger.getLogger(BoardService.class.getName());

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MultiFileService multiFileService;
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
     * 게시물 등록 전 유효성 확인
     * @param bindingResult
     * @return boardFormInsertErrorDTO
     */
    public BoardFormInsertErrorDTO errorCheckBeforeRegisterBoard(BindingResult bindingResult) {
        BoardFormInsertErrorDTO boardFormInsertErrorDTO = new BoardFormInsertErrorDTO();

        bindingResult.getFieldErrors().forEach(error -> {
            String field = error.getField();
            String errorMessage = error.getDefaultMessage();

            // 조건에 맞는 필드에 에러 메시지 설정
            if ("categoryId".equals(field)) {
                boardFormInsertErrorDTO.setCategoryIdErrorMessage(errorMessage);
            } else if ("userName".equals(field)) {
                boardFormInsertErrorDTO.setUserNameErrorMessage(errorMessage);
            } else if ("password".equals(field)) {
                boardFormInsertErrorDTO.setPasswordErrorMessage(errorMessage);
            } else if ("passwordCheck".equals(field)) {
                boardFormInsertErrorDTO.setPasswordCheckErrorMessage(errorMessage);
            } else if ("title".equals(field)) {
                boardFormInsertErrorDTO.setTitleErrorMessage(errorMessage);
            } else if ("content".equals(field)) {
                boardFormInsertErrorDTO.setContentsErrorMessage(errorMessage);
            }
        });

        return boardFormInsertErrorDTO;
    }

    /**
     * 게시물 등록 및 파일 업로드
     * @param inDTO
     * @return
     */
    public int registerBoard(BoardFormInsertInDTO inDTO) {
        // 데이터 처리 셋팅
        BoardInfoProcessDTO boardInfoProcessDTO = new BoardInfoProcessDTO();
        boardInfoProcessDTO.setCategoryId(inDTO.getCategoryId());
        boardInfoProcessDTO.setUserName(inDTO.getUserName());
        boardInfoProcessDTO.setPassword(inDTO.getPassword());
        boardInfoProcessDTO.setTitle(inDTO.getTitle());
        boardInfoProcessDTO.setContents(inDTO.getContents());

        // 파일 업로드 및 값 셋팅
        StringBuilder sb = new StringBuilder();
        if (inDTO.getFileId() != null) {
            for(MultipartFile file : inDTO.getFileId()) {
                try {
                    MultiFileProcessDTO multiFileProcessDTO = multiFileService.upload(file);
                    if (multiFileProcessDTO != null) {
                        sb.append(multiFileProcessDTO.getFileId()).append(" | ");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        boardInfoProcessDTO.setFileId(sb.toString());

        int cnt = boardMapper.registerBoard(boardInfoProcessDTO);

        return cnt;
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
