package com.study.api.controller;

import com.study.api.config.ResponseDTO;
import com.study.api.model.in.BoardFormInsertInDTO;
import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.mapstruct.BoardMapStruct;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.CategoryListOutDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import com.study.api.service.BoardService;
import com.study.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = {"/boards/free/*"})
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 게시판 목록 화면
     * @param boardSearchInDTO
     * @return outDTO
     */
    @GetMapping(value = "list")
    public ResponseDTO<BoardSearchOutDTO> list(@ModelAttribute BoardSearchInDTO boardSearchInDTO) {

        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        int pageSize = 10;
        BoardSearchProcessDTO boardSearchProcessDTO = boardMapStruct.boardSearchInDtoToBoardSearchProcessDto(boardSearchInDTO, pageSize);
        boardMapStruct.setOffsetByNowPage(boardSearchInDTO, boardSearchProcessDTO);

        ResponseDTO<BoardSearchOutDTO> outDTO = boardService.boardSearch(boardSearchProcessDTO);

        return outDTO;
    }

    /**
     * 카테고리 목록 조회
     * @return result
     */
    @GetMapping(value = "categoryList")
    public ResponseDTO<List<CategoryListOutDTO>> categoryList() {

        ResponseDTO<List<CategoryListOutDTO>> outDTO = categoryService.getCategoryAllList();

        return outDTO;
    }

    /**
     * 게시물 등록 및 파일 업로드
     * @param boardFormInsertInDTO
     * @return
     */
    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertData(@ModelAttribute @Valid BoardFormInsertInDTO boardFormInsertInDTO, BindingResult bindingResult) throws IOException {

        int cnt = boardService.registerBoard(boardFormInsertInDTO);

        if (cnt == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        /* todo.
            swagger에서 test 할 때 파일을 선택하지 않고 요청을 보내면
            Failed to convert value of type 'java.lang.String' to required type 'java.util.List' error가 있어서
            내가 지정한 validation 외에 처리할 것이 생김.
            유효성 확인해서 해당되는 것이 있으면 ErrorDTO에 담아 반환하려 했으나 위의 대책을 찾지 못함.
        * */
//        // 유효성 확인 후 등록
//        if (bindingResult.hasErrors()) {
//            BoardFormInsertErrorDTO boardFormInsertErrorDTO = boardService.errorCheckBeforeRegisterBoard(bindingResult);
//
//            return new ResponseEntity<>(boardFormInsertErrorDTO, HttpStatus.BAD_REQUEST);
//        } else {
//            int cnt = boardService.registerBoard(boardFormInsertInDTO);
//
//            if (cnt == 1) {
//                return new ResponseEntity<>(HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
    }

}
