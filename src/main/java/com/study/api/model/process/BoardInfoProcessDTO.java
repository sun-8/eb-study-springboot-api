package com.study.api.model.process;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardInfoProcessDTO {
    private int id;
    private String categoryId;
    private String userName;
    private String password;
    private String title;
    private String contents;
    private List<MultipartFile> multiFileId;
    private String fileId;
    private String registerDatetime;
    private String modifyDatetime;
}
