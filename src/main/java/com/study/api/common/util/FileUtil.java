package com.study.api.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.StringTokenizer;

public class FileUtil {

    /**
     * 파일이 image인지 확인
     * @param file
     * @return boolean
     */
    public static boolean vaildImgFile(MultipartFile file) {
        String fileContentType = file.getContentType();
        if (!CommonUtil.isEmpty(fileContentType)) {
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
