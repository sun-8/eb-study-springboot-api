package com.study.api.service;

import com.study.api.config.ResponseDTO;
import com.study.api.mapper.BoardMapper;
import com.study.api.model.mapstruct.BoardMapStruct;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.board.BoardListDTO;
import com.study.api.model.process.BoardInfoProcessDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import com.study.api.model.process.MultiFileProcessDTO;
import com.study.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

            outDTO.setResponseCode(Message.SUCCESS_CODE_0000);
            outDTO.setResponseData(boardSearchOutDTO);

        } catch (Exception e) {
            log.info(e.getMessage());
            outDTO.setResponseCode(Message.ERROR_CODE_9999);
            outDTO.setResponseMessage(Message.ERROR_MESSAGE_9999);
        }

        return outDTO;
    }

    /**
     * 게시물 등록 및 파일 업로드
     * @param processDTO
     * @return
     */
    public ResponseDTO<Void> registerBoard(BoardInfoProcessDTO processDTO) {
        ResponseDTO<Void> outDTO = new ResponseDTO<>();

        // todo. 에러 처리 응답 규격화가 적절한가?
        // 파일 업로드 및 값 셋팅
        StringBuilder sb = new StringBuilder();
        if (processDTO.getMultiFileId() != null) {
            for(MultipartFile file : processDTO.getMultiFileId()) {
                try {
                    MultiFileProcessDTO multiFileProcessDTO = multiFileService.imgUpload(file);
                    if (multiFileProcessDTO != null) {
                        sb.append(multiFileProcessDTO.getFileId()).append(" | ");
                    }
                } catch (Exception e) {
                    log.info(e.getMessage());
                    outDTO.setResponseCode(Message.ERROR_CODE_9997);
                    outDTO.setResponseMessage(Message.ERROR_MESSAGE_9997);
                    return outDTO;
                }
            }
        }
        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        boardMapStruct.setFileId(sb.toString(), processDTO);

        int cnt = boardMapper.registerBoard(processDTO);
        if (cnt == 0) {
            outDTO.setResponseCode(Message.ERROR_CODE_9999);
            outDTO.setResponseMessage(Message.ERROR_MESSAGE_9999);
        } else {
            outDTO.setResponseCode(Message.SUCCESS_CODE_0000);
        }

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
