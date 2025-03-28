package com.study.api.common.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class FileUtil {

    private final List<String> FORBIDDEN_EXTENSIONS = List.of("exe", "xls", "xlsx", "pdf");

    /**
     * 파일이 image인지 확인
     * @param file
     * @return boolean
     */
    public boolean validImgFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isBlank()) {
            return false;
        }

        // 확장자 추출
        String extension = null;
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            extension = ""; // 확장자가 없는 경우
        } else {
            extension = fileName.substring(lastDotIndex + 1);
        }

        return !FORBIDDEN_EXTENSIONS.contains(extension.toLowerCase());
    }
}
