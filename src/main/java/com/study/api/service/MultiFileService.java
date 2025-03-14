package com.study.api.service;

import com.study.api.mapper.MultiFileMapper;
import com.study.api.model.process.MultiFileProcessDTO;
import com.study.util.CommonUtil;
import com.study.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class MultiFileService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${system.upload-path}")
    private String uploadPath;

    @Autowired
    private MultiFileMapper multiFileMapper;

    /**
     * 이미지 파일 업로드
     * @param file
     * @throws IOException
     */
    public MultiFileProcessDTO imgUpload(MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            return null;
        }
        MultiFileProcessDTO multiFileProcessDTO = new MultiFileProcessDTO();

        // todo. 파일 업로드 확장자 제한 확인
        // 이미지 파일만 업로드 가능
        if(FileUtil.vaildImgFile(file)) {
            // 파일 정보 셋팅
            multiFileProcessDTO = this.fileInfoSetting(file);

            // 파일 저장
            int insertCnt = this.registerMultiFile(multiFileProcessDTO);

            File saveFile = new File(multiFileProcessDTO.getFilePath(), multiFileProcessDTO.getFileId());
            // 파일 업로드
            file.transferTo(saveFile);
        } else {
            throw new Exception();
        }
        return multiFileProcessDTO;
    }

    /**
     * 파일 다운로드
     * @param fileId
     * @return
     */
    public ResponseEntity<Resource> download(String fileId) throws IOException {

        MultiFileProcessDTO multiFileProcessDTO = this.getMultiFile(new MultiFileProcessDTO(fileId));

        // todo. byte buffer 사용
        Resource resource = new FileSystemResource(multiFileProcessDTO.getFilePath() + "\\" + fileId);

        // URL 인코딩을 사용하여 파일 이름을 처리 - 한글 처리
        String encodedFileName = URLEncoder.encode(multiFileProcessDTO.getFileName(), "UTF-8")
                .replaceAll("\\+", "%20"); // '+' 대신 공백을 %20으로 변경

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(multiFileProcessDTO.getFileType() + "/" + multiFileProcessDTO.getFileExtend()));
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /**
     * 파일 조회
     * @param multiFileProcessDTO
     * @return
     */
    public MultiFileProcessDTO getMultiFile(MultiFileProcessDTO multiFileProcessDTO) {

        MultiFileProcessDTO rtnDTO = multiFileMapper.getMultiFile(multiFileProcessDTO);

        return rtnDTO;
    }

    /**
     * 업로드 파일 저장
     * @param multiFileProcessDTO
     * @return
     */
    public int registerMultiFile(MultiFileProcessDTO multiFileProcessDTO) {

        int cnt = multiFileMapper.registerMultiFile(multiFileProcessDTO);

        return cnt;
    }

    /**
     * 파일 정보 셋팅
     * @param file
     * @return
     */
    public MultiFileProcessDTO fileInfoSetting(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String fileFolder = CommonUtil.localNowDate("yyyyMMdd");
        String filePath = uploadPath + fileFolder;
        // 오늘일자 폴더 없으면 생성
        File filePathFolder = new File(filePath);
        if (!filePathFolder.exists()) {
            filePathFolder.mkdir();
        }
        long fileSize = file.getSize();
        String fileContentType = file.getContentType();
        StringTokenizer st = new StringTokenizer(fileContentType, "/");
        String fileType = st.nextToken();
        String fileExtend = st.nextToken();
        // 멀티스레드 고려하여 UUID 생성
        UUID uuid = UUID.randomUUID();
        String fileId = fileFolder + "_" + uuid + "_" + fileName;

        return MultiFileProcessDTO.builder().
                fileId(fileId).
                fileFolder(fileFolder).
                fileName(fileName).
                filePath(filePath).
                fileSize(fileSize).
                fileType(fileType).
                fileExtend(fileExtend).
                build();
    }
}
