package com.study.api.model.process;

import lombok.Data;

@Data
public class BoardInfoProcessDTO {
    private String categoryId;
    private String userName;
    private String password;
    private String title;
    private String contents;
    private String fileId;
    private String registerDatetime;
    private String modifyDatetime;
}
