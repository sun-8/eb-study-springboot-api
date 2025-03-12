package com.study.api.service;

import com.study.api.config.ResponseDTO;
import com.study.api.mapper.BoardMapper;
import com.study.api.model.error.BoardFormInsertErrorDTO;
import com.study.api.model.in.BoardFormInsertInDTO;
import com.study.api.model.mapstruct.BoardMapStruct;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.board.BoardListDTO;
import com.study.api.model.process.BoardInfoProcessDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import com.study.api.model.process.MultiFileProcessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MultiFileService multiFileService;
    @Autowired
    private BoardMapper boardMapper;

    /**
     * 게시판 목록 조회
     * @param boardSearchProcessDTO
     * @return outDTO
     */
    public ResponseDTO<BoardSearchOutDTO> boardSearch(BoardSearchProcessDTO boardSearchProcessDTO) {

        ResponseDTO<BoardSearchOutDTO> outDTO = new ResponseDTO<>();

        try{
            // 페이지 계산
            int searchDataCount = this.getBoardSearchCount(boardSearchProcessDTO);
            // 게시판 목록
            List<BoardListDTO> boardList = this.getBoardSearchList(boardSearchProcessDTO);

            BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
            boardMapStruct.setSearchDataCount(searchDataCount, boardSearchProcessDTO);
            boardMapStruct.setLastPageBySearchDataCount(searchDataCount, boardSearchProcessDTO);

            BoardSearchOutDTO boardSearchOutDTO = new BoardSearchOutDTO(boardSearchProcessDTO.getNowPage(),
                                                                        searchDataCount,
                                                                        1,
                                                                        boardSearchProcessDTO.getLastPage(),
                                                                        boardList);

            outDTO.setResponseCode("0000");
            outDTO.setResponseData(boardSearchOutDTO);

        } catch (Exception e) {
            log.info(e.getMessage());
            outDTO.setResponseCode("9999");
            outDTO.setResponseMessage("처리 중 오류가 발생했습니다.");
        }

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
        // 데이터 처리 셋팅 - 이 과정은 controller에서.. map structure
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
