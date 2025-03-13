package com.study.api.model.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardFormInsertInDTO {
    /* todo.
        class 대신 record 사용하려 했으나
        spring validation 적용 안됨.
    * */
    @NotBlank(message = "카테고리를 선택해주세요.")
    private String categoryId;

    @NotBlank(message = "작성자를 입력해주세요.")
    @Size(min = 3, max = 4, message = "3글자 이상, 5글자 미만으로 입력해주세요.")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 15, message = "4글자 이상, 16글자 미만으로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~@#$!%*?&])[a-zA-Z\\d~@#$!%*?&]{4,15}$", message = "영문/숫자/특수문자를 포함하여 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 4, max = 99, message = "4글자 이상, 100글자 미만으로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 4, max = 1999, message = "4글자 이상, 2000글자 미만으로 입력해주세요.")
    private String contents;

    private List<MultipartFile> fileId;
}
