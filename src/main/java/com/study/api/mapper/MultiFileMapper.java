package com.study.api.mapper;

import com.study.api.model.process.MultiFileProcessDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MultiFileMapper {

    /**
     * 파일 정보 조회
     * @param multiFileProcessDTO
     * @return
     */
    public MultiFileProcessDTO getMultiFile(MultiFileProcessDTO multiFileProcessDTO);

    /**
     * 업로드 파일 저장
     * @param multiFileProcessDTO
     * @return
     */
    public int registerMultiFile(MultiFileProcessDTO multiFileProcessDTO);
}
