package com.study.api.model.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiFileProcessDTO {

    private String fileId;
    private String fileFolder;
    private String fileName;
    private String filePath;
    private long fileSize;
    private String fileType;
    private String fileExtend;
    private String regDttm;
    private String modDttm;

    public MultiFileProcessDTO(String fileId) {
        this.fileId = fileId;
    }
}
