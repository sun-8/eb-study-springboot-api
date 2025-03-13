package com.study.api.controller;

import com.study.api.config.ErrorDTO;
import com.study.api.config.ResponseDTO;
import com.study.api.model.in.BoardFormInsertInDTO;
import com.study.api.model.in.BoardSearchInDTO;
import com.study.api.model.mapstruct.BoardMapStruct;
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
    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<?> insertData(@ModelAttribute @Valid BoardFormInsertInDTO boardFormInsertInDTO, BindingResult bindingResult) throws IOException {
        // todo. swagger 에서만 file upload를 하지 않았을 때 String을 배열로 바꿀 수 없다는 error 생김

        BoardMapStruct boardMapStruct = BoardMapStruct.INSTANCE;

        // 유효성 확인 후 등록
        if (bindingResult.hasErrors()) {
            ResponseDTO<List<ErrorDTO>> outErrorDTO = new ResponseDTO<>();
            outErrorDTO.setResponseCode(Message.ERROR_CODE_9998);
            outErrorDTO.setResponseMessage(Message.ERROR_MESSAGE_9998);

            List<ErrorDTO> errorListDTO = boardMapStruct.errorToBoardFormInsertErrorDTOs(bindingResult.getFieldErrors());
            outErrorDTO.setResponseData(errorListDTO);

            return outErrorDTO;
        } else {
            BoardInfoProcessDTO boardInfoProcessDTO = boardMapStruct.boardFormInsertInDtoToBoardInfoProcessDto(boardFormInsertInDTO);
            ResponseDTO<Void> outDTO = boardService.registerBoard(boardInfoProcessDTO);

            return outDTO;
        }
    }

}
