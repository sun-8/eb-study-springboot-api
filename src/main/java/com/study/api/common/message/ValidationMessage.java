package com.study.api.common.message;

public class ValidationMessage {
    public final static String NOTBLANK = "필수 입력값입니다.";

    public final static String SIZE_3_TO_4 = "3글자 이상, 5글자 미만으로 입력해주세요.";
    public final static String SIZE_4_TO_15 = "4글자 이상, 16글자 미만으로 입력해주세요.";
    public final static String SIZE_4_TO_99 = "4글자 이상, 100글자 미만으로 입력해주세요.";
    public final static String SIZE_4_TO_1999 = "4글자 이상, 2000글자 미만으로 입력해주세요.";

    public final static String PASSWORD_PATTERN_REGEXP = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~@#$!%*?&])[a-zA-Z\\d~@#$!%*?&]{4,15}$";
    public final static String PASSWORD_PATTERN_MESSAGE = "영문/숫자/특수문자를 포함하여 입력해주세요.";
}
