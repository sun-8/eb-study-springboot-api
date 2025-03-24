package com.study.api.controller;

import com.study.api.config.ErrorDTO;
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
import com.study.message.Message;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
        try {
            BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;
            int pageSize = 10;
            BoardSearchProcessDTO boardSearchProcessDTO = boardMapStruct.boardSearchInDtoToBoardSearchProcessDto(boardSearchInDTO, pageSize);
            boardMapStruct.setOffsetByNowPage(boardSearchInDTO, boardSearchProcessDTO);

            BoardSearchOutDTO outDTO = boardService.boardSearch(boardSearchProcessDTO);

            return ResponseEntity.ok(outDTO);

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception(Message.ERROR_MESSAGE);
        }
    }

    /**
     * 카테고리 목록 조회
     * @return result
     */
    @GetMapping(value = "categoryList")
    public ResponseEntity<List<CategoryListOutDTO>> categoryList() throws Exception {
        try {
            List<CategoryListOutDTO> outDTO = categoryService.getCategoryAllList();

            return ResponseEntity.ok(outDTO);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception(Message.ERROR_MESSAGE);
        }
    }

    /**
     * 게시물 등록 및 파일 업로드
     * @param boardFormInsertInDTO
     * @return
     */
    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertData(@ModelAttribute @Valid BoardFormInsertInDTO boardFormInsertInDTO, BindingResult bindingResult) throws Exception {
        // todo. swagger 에서만 file upload를 하지 않았을 때 String을 배열로 바꿀 수 없다는 error 생김 - 처리를 해줘야 할 것 같다.

        try {
            BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;

            // 유효성 확인 후 등록
            if (bindingResult.hasErrors()) {
                List<ErrorDTO> errorListDTO = boardMapStruct.errorToBoardFormErrorDTOs(bindingResult.getFieldErrors());

                return ResponseEntity.ok(errorListDTO);
            } else {
                BoardInfoProcessDTO boardInfoProcessDTO = boardMapStruct.boardFormInsertInDtoToBoardInfoProcessDto(boardFormInsertInDTO);
                int cnt = boardService.registerBoard(boardInfoProcessDTO);

                return ResponseEntity.ok(cnt);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception(Message.ERROR_MESSAGE);
        }
    }

    /**
     * 게시물 상세 조회
     * @param seq
     * @return
     * @throws Exception
     */
    @GetMapping("{seq}")
    public ResponseEntity<BoardInfoOutDTO> detail(@PathVariable("seq") String seq) throws Exception {
        try {
            BoardInfoOutDTO outDTO = boardService.getBoardInfo(seq);

            return ResponseEntity.ok(outDTO);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception(Message.ERROR_MESSAGE);
        }
    }

    /**
     * 게시물 수정 및 파일 업로드
     * @param boardFormUpdateInDTO
     * @return
     */
    @PutMapping(value = "modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateData(@ModelAttribute @Valid BoardFormUpdateInDTO boardFormUpdateInDTO, BindingResult bindingResult) throws Exception {
        // todo. swagger 에서만 file upload를 하지 않았을 때 String을 배열로 바꿀 수 없다는 error 생김 - 처리를 해줘야 할 것 같다.

        try {
            BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;

            // 유효성 확인 후 등록
            if (bindingResult.hasErrors()) {
                List<ErrorDTO> errorListDTO = boardMapStruct.errorToBoardFormErrorDTOs(bindingResult.getFieldErrors());

                return ResponseEntity.ok(errorListDTO);
            } else {
                BoardInfoProcessDTO boardInfoProcessDTO = boardMapStruct.boardFormUpdateInDtoToBoardInfoProcessDto(boardFormUpdateInDTO);
                int cnt = boardService.modifyBoard(boardInfoProcessDTO);

                return ResponseEntity.ok(cnt);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception(Message.ERROR_MESSAGE);
        }
    }
}
