package com.study.api.controller;

import com.study.api.model.in.BoardCheckPasswordInDTO;
import com.study.api.model.in.BoardFormInsertInDTO;
import com.study.api.model.in.BoardFormUpdateInDTO;
import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.mapstruct.BoardMapStruct;
import com.study.api.model.out.BoardInfoOutDTO;
import com.study.api.model.out.BoardSearchOutDTO;
import com.study.api.model.out.CategoryListOutDTO;
import com.study.api.model.process.BoardInfoProcessDTO;
import com.study.api.model.process.BoardSearchProcessDTO;
import com.study.api.service.BoardService;
import com.study.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BoardSearchOutDTO> list(@ModelAttribute BoardSearchInDTO boardSearchInDTO) throws Exception {
        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        int pageSize = 10;
        BoardSearchProcessDTO boardSearchProcessDTO = boardMapStruct.boardSearchInDtoToBoardSearchProcessDto(boardSearchInDTO, pageSize);
        boardMapStruct.setOffsetByNowPage(boardSearchInDTO, boardSearchProcessDTO);

        BoardSearchOutDTO outDTO = boardService.boardSearch(boardSearchProcessDTO);

        return ResponseEntity.ok(outDTO);
    }

    /**
     * 카테고리 목록 조회
     * @return result
     */
    @GetMapping(value = "categoryList")
    public ResponseEntity<List<CategoryListOutDTO>> categoryList() throws Exception {
        List<CategoryListOutDTO> outDTO = categoryService.getCategoryAllList();

        return ResponseEntity.ok(outDTO);
    }

    /**
     * 게시물 등록 및 파일 업로드
     * @param boardFormInsertInDTO
     * @return
     */
    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertData(@ModelAttribute @Valid BoardFormInsertInDTO boardFormInsertInDTO) throws Exception {
        // todo.
        //  swagger 에서만 file upload를 하지 않았을 때 String을 배열로 바꿀 수 없다는 error 생김 - 처리를 해줘야 할 것 같다.
        //  swagger 의 "Send empty value"를 check하지 않고 보내면 해당 에러가 발생하지 않음. - 기본값 변경하는 방법 알아보기.
        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        BoardInfoProcessDTO boardInfoProcessDTO = boardMapStruct.boardFormInsertInDtoToBoardInfoProcessDto(boardFormInsertInDTO);
        int cnt = boardService.registerBoard(boardInfoProcessDTO);

        return ResponseEntity.ok(cnt);
    }

    /**
     * 게시물 상세 조회
     * @param seq
     * @return
     * @throws Exception
     */
    @GetMapping("{seq}")
    public ResponseEntity<BoardInfoOutDTO> detail(@PathVariable("seq") String seq) throws Exception {
        BoardInfoOutDTO outDTO = boardService.getBoardInfo(seq);

        return ResponseEntity.ok(outDTO);
    }

    /**
     * 게시판 비밀번호 확인
     * @param boardCheckPasswordInDTO
     * @return
     */
    @ResponseBody
    @GetMapping("checkPassword")
    public ResponseEntity<String> checkPassword(@ModelAttribute BoardCheckPasswordInDTO boardCheckPasswordInDTO) throws Exception {

        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        BoardInfoProcessDTO boardInfoProcessDTO = boardMapStruct.boardCheckPasswordInDtoToBoardInfoProcessDto(boardCheckPasswordInDTO);
        int cnt = boardService.checkPasword(boardInfoProcessDTO);

        if (cnt == 0) {
            return ResponseEntity.ok("NO");
        } else {
            return ResponseEntity.ok("YES");
        }
    }

    /**
     * 게시물 수정 및 파일 업로드
     * @param boardFormUpdateInDTO
     * @return
     */
    @PutMapping(value = "modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateData(@ModelAttribute @Valid BoardFormUpdateInDTO boardFormUpdateInDTO) throws Exception {
        // todo.
        //  swagger 에서만 file upload를 하지 않았을 때 String을 배열로 바꿀 수 없다는 error 생김 - 처리를 해줘야 할 것 같다.
        //  swagger 의 "Send empty value"를 check하지 않고 보내면 해당 에러가 발생하지 않음. - 기본값 변경하는 방법 알아보기.
        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
        BoardInfoProcessDTO boardInfoProcessDTO = boardMapStruct.boardFormUpdateInDtoToBoardInfoProcessDto(boardFormUpdateInDTO);
        int cnt = boardService.modifyBoard(boardInfoProcessDTO);

        return ResponseEntity.ok(cnt);
    }

    /**
     * 게시물 삭제
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "delete")
    public ResponseEntity<Integer> deleteData(@RequestParam("id") int id) throws Exception{

        int cnt = boardService.deleteBoard(id);

        return ResponseEntity.ok(cnt);
    }
}
