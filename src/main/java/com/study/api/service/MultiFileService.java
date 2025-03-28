package com.study.api.service;

import com.study.api.mapper.MultiFileMapper;
import com.study.api.model.process.MultiFileProcessDTO;
import com.study.api.common.util.CommonUtil;
import com.study.api.common.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class MultiFileService {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${system.upload-path}")
    private String uploadPath;

    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private FileUtil fileUtil;
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

        // 파일 업로드 제한 검증
        if(fileUtil.validImgFile(file)) {
            // 파일 정보 셋팅
            multiFileProcessDTO = this.fileInfoSetting(file);

            // 파일 저장
            int insertCnt = this.registerMultiFile(multiFileProcessDTO);

            File saveFile = new File(multiFileProcessDTO.getFilePath(), multiFileProcessDTO.getFileId());
            // 파일 업로드
            file.transferTo(saveFile);
        }

        return multiFileProcessDTO;
    }

    /**
     * 파일 다운로드
     * @param fileId
     * @return
     */
    public ResponseEntity<StreamingResponseBody> download(String fileId) throws IOException {

        MultiFileProcessDTO multiFileProcessDTO = this.getMultiFile(new MultiFileProcessDTO(fileId));
        Path path = Paths.get(multiFileProcessDTO.getFilePath() + "\\" + fileId);

        // 해당 경로에 파일 없을 때
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        // byte buffer 사용하여 조금씩 다운로드 진행
        StreamingResponseBody streamingResponseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                try(InputStream inputStream = Files.newInputStream(path)) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, read);
                        outputStream.flush();
                    }
                }
            }
        };

        // URL 인코딩을 사용하여 파일 이름을 처리 - 한글 처리
        String encodedFileName = URLEncoder.encode(multiFileProcessDTO.getFileName(), "UTF-8")
                .replaceAll("\\+", "%20"); // '+' 대신 공백을 %20으로 변경

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(multiFileProcessDTO.getFileType() + "/" + multiFileProcessDTO.getFileExtend()));
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return ResponseEntity.ok().headers(headers).body(streamingResponseBody);
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
        String fileFolder = commonUtil.localNowDate("yyyyMMdd");
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
