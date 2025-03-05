package com.study.api.controller;

import com.study.api.model.in.BoardFormInsertInDTO;
import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.CategoryListOutDTO;
import com.study.api.service.BoardService;
import com.study.api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = {"/boards/free/*"})
public class BoardController {
    Logger logger = Logger.getLogger(BoardController.class.getName());

    @Autowired
    private BoardService boardService;
    @Autowired
    private CategoryService categoryService;

    /* todo.
        @JsonProperty와 @ModelAttribute 상호작용 안됨.
        --
        @ModelAttribute는 HTTP 요청 파라미터를 DTO 객체에 바인딩할 때 사용되며,
        이 과정에서 @JsonProperty와 같은 어노테이션은 영향을 미치지 않는다고 함.
        @ModelAttribute가 주로 폼 데이터나 쿼리 파라미터를 바인딩하는 데 사용되기 때문에
        이 과정에서는 Jackson의 JSON 처리 기능이 사용되지 않으며,
        @JsonProperty와 같은 어노테이션이 적용되지 않는다고 함.
        --
        그렇다면 get방식일 때 각각 param을 지정해주어야 하는가?
        아니면 굳이 JsonProperty를 사용하지 않아도 되는가?
    * */
    /**
     * 게시판 목록 화면
     * @param searchRegisterDateStart
     * @param searchRegisterDateEnd
     * @param searchCategory
     * @param searchWord
     * @param nowPage
     * @return result
     */
    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BoardSearchOutDTO> list(@RequestParam(required = false, defaultValue = "2025-01-01") String searchRegisterDateStart,
                                                  @RequestParam(required = false, defaultValue = "2025-12-31") String searchRegisterDateEnd,
                                                  @RequestParam(required = false) String searchCategory,
                                                  @RequestParam(required = false) String searchWord,
                                                  @RequestParam(defaultValue = "1") int nowPage) {

        BoardSearchInDTO boardSearchInDTO = new BoardSearchInDTO();
        boardSearchInDTO.setSearchRegisterDateStart(searchRegisterDateStart);
        boardSearchInDTO.setSearchRegisterDateEnd(searchRegisterDateEnd);
        boardSearchInDTO.setSearchCategory(searchCategory);
        boardSearchInDTO.setSearchWord(searchWord);
        boardSearchInDTO.setNowPage(nowPage);

        BoardSearchOutDTO outDTO = boardService.boardSearch(boardSearchInDTO);

        return new ResponseEntity<>(outDTO, HttpStatus.OK);
    }

    /**
     * 카테고리 목록 조회
     * @return result
     */
    @GetMapping(value = "categoryList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryListOutDTO>> categoryList() {

        List<CategoryListOutDTO> outDTO = categoryService.getCategoryAllList();

        return new ResponseEntity<>(outDTO, HttpStatus.OK);
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
