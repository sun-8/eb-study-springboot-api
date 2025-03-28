package com.study.api.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.StringTokenizer;

@Component
public class FileUtil {

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 파일이 image인지 확인
     * @param file
     * @return boolean
     */
    public boolean vaildImgFile(MultipartFile file) {
        String fileContentType = file.getContentType();
        if (!commonUtil.isEmpty(fileContentType)) {
            StringTokenizer st = new StringTokenizer(fileContentType, "/");
            String fileType = st.nextToken();
            String fileExtend = st.nextToken();

            if (fileType.equals("image")) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
}
