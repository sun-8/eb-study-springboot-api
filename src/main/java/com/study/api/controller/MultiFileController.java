package com.study.api.controller;

import com.study.api.service.MultiFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

@Slf4j
@RestController
public class MultiFileController {

    @Autowired
    private MultiFileService multiFileService;

    /**
     * 파일 다운로드
     * @param fileId
     * @return
     */
    @ResponseBody
    @GetMapping("/download")
    public ResponseEntity<StreamingResponseBody> download(@RequestParam("fileId") String fileId) {
        ResponseEntity<StreamingResponseBody> rtn = null;

        try {
            rtn = multiFileService.download(fileId);
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        return rtn;
    }
}
