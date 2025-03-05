package com.study.api.controller;

import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.CategoryListOutDTO;
import com.study.api.model.out.ResponseDTO;
import com.study.api.service.BoardService;
import com.study.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseDTO<BoardSearchOutDTO> list(@RequestParam(required = false, defaultValue = "2025-01-01") String searchRegisterDateStart,
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

        ResponseDTO<BoardSearchOutDTO> result = new ResponseDTO<>();
        result.setResponseCode("0000");
        result.setResponseMessage("success");
        result.setData(outDTO);
        return result;
    }

    /**
     * 카테고리 목록 조회
     * @return result
     */
    @GetMapping(value = "categoryList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO<List<CategoryListOutDTO>> categoryList() {

        List<CategoryListOutDTO> outDTO = categoryService.getCategoryAllList();

        ResponseDTO<List<CategoryListOutDTO>> result = new ResponseDTO<>();
        result.setResponseCode("0000");
        result.setResponseMessage("success");
        result.setData(outDTO);
        return result;
    }

}
