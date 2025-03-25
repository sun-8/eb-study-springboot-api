package com.study.api.service;

import com.study.api.mapper.BoardMapper;
import com.study.api.model.mapstruct.BoardMapStruct;
import com.study.api.model.out.BoardInfoOutDTO;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.board.BoardListDTO;
import com.study.api.model.process.BoardInfoProcessDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import com.study.api.model.process.MultiFileProcessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public BoardSearchOutDTO boardSearch(BoardSearchProcessDTO boardSearchProcessDTO) throws Exception {
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

        return boardSearchOutDTO;
    }

    /**
     * 게시물 등록 및 파일 업로드
     * @param processDTO
     * @return
     */
    public int registerBoard(BoardInfoProcessDTO processDTO) throws Exception {
        // 파일 업로드 및 값 셋팅
        StringBuilder sb = new StringBuilder();
        if (processDTO.getMultiFileId() != null) {
            for(MultipartFile file : processDTO.getMultiFileId()) {
                MultiFileProcessDTO multiFileProcessDTO = multiFileService.imgUpload(file);
                if (multiFileProcessDTO != null) {
                    sb.append(multiFileProcessDTO.getFileId()).append(" | ");
                }
            }
        }
        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        boardMapStruct.setFileId(sb.toString(), processDTO);

        int cnt = boardMapper.registerBoard(processDTO);

        return cnt;
    }

    /**
     * 게시물 단건 조회
     * @param seq
     * @return
     */
    public BoardInfoOutDTO getBoardInfo(String seq) throws Exception {

        int cnt = boardMapper.modifyBoardViews(seq);
        BoardInfoOutDTO boardInfoOutDTO = boardMapper.getBoardInfo(seq);

        return boardInfoOutDTO;
    }

    /**
     * 게시물 수정 및 파일 업로드
     * @param processDTO
     * @return
     */
    public int modifyBoard(BoardInfoProcessDTO processDTO) throws Exception {
        // 파일 업로드 및 값 셋팅
        StringBuilder sb = new StringBuilder();
        if (processDTO.getMultiFileId() != null) {
            for(MultipartFile file : processDTO.getMultiFileId()) {
                MultiFileProcessDTO multiFileProcessDTO = multiFileService.imgUpload(file);
                if (multiFileProcessDTO != null) {
                    sb.append(multiFileProcessDTO.getFileId()).append(" | ");
                }
            }
        }
        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        boardMapStruct.setFileId(sb.toString(), processDTO);

        int cnt = boardMapper.modifyBoard(processDTO);

        return cnt;
    }

    /**
     * 비밀번호 확인
     * @param processDTO
     * @return
     * @throws Exception
     */
    public int checkPasword(BoardInfoProcessDTO processDTO) throws Exception {

        int cnt = boardMapper.checkPassword(processDTO);

        return cnt;
    }

    /**
     * 게시글 삭제
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteBoard(int id) throws Exception {

        int cnt = boardMapper.deleteBoard(id);

        return cnt;
    }

    /**
     * 게시글 총 개수 (검색조건)
     * @param boardSearchProcessDTO
     * @return cnt
     */
    public int getBoardSearchCount(BoardSearchProcessDTO boardSearchProcessDTO) throws Exception {

        int cnt = boardMapper.getBoardSearchCount(boardSearchProcessDTO);

        return cnt;
    }

    /**
     * 게시판 목록 조회 (검색조건, 페이징)
     * @param boardSearchProcessDTO
     * @return boardList
     */
    public List<BoardListDTO> getBoardSearchList(BoardSearchProcessDTO boardSearchProcessDTO) throws Exception {

        List<BoardListDTO> boardList = boardMapper.getBoardSearchList(boardSearchProcessDTO);

        return boardList;
    }
}
