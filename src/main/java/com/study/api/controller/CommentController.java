package com.study.api.controller;

import com.study.api.model.in.CommentFormInsertInDTO;
import com.study.api.model.mapstruct.CommentMapStruct;
import com.study.api.model.out.CommentOutDTO;
import com.study.api.model.process.CommentInfoProcessDTO;
import com.study.api.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = {"boards/comment/*"})
public class CommentController {
    @Autowired
    private CommentService commentService;


    /**
     * 게시글 댓글 조회
     * @param key
     * @return
     */
    @ResponseBody
    @GetMapping("list")
    public ResponseEntity<CommentOutDTO> list(@RequestParam("id") String key) {

        CommentOutDTO outDTO = commentService.getCommentList(Integer.parseInt(key));

        return ResponseEntity.ok(outDTO);
    }

    /**
     * 게시글에 대한 댓글 등록
     * @param commentFormInsertInDTO
     * @return
     */
    @ResponseBody
    @PostMapping("register")
    public ResponseEntity<Integer> insertData(@RequestBody CommentFormInsertInDTO commentFormInsertInDTO) {

        CommentMapStruct commentMapStruct = CommentMapStruct.INSTANCE;
        CommentInfoProcessDTO commentInfoProcessDTO = commentMapStruct.commentFromInsertInDtoToCommentInfoProcessDto(commentFormInsertInDTO);

        int cnt = commentService.registerComment(commentInfoProcessDTO);

        return ResponseEntity.ok(cnt);
    }
}
