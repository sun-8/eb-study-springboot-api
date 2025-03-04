package com.study.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 공통 함수
 */
public class CommonUtil {

    // 문자열 -----------------------------------

    /**
     * 문자열이 비어있는지 확인
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 문자열이 비어있으면 기본값 반환
     * @param str
     * @param defaultStr
     * @return
     */
    public static String nvl(String str, String defaultStr) {
        if (str == null || str.equals("")) {
            return defaultStr;
        } else {
            return str;
        }
    }

    /**
     * 문자열을 특정 길이가 넘으면 잘라서 반환
     * @param str
     * @param length
     * @return
     */
    public static String stringCut(String str, int length) {
        if (str != null && str.length() > length) {
            return str.substring(0, length) + "...";
        } else {
            return str;
        }
    }

    // 날짜 -------------------------------------

    /**
     * 주어진 날짜를 원하는 format으로 변경
     * @param localDate
     * @param pattern
     * @return
     */
    public static DateTimeFormatter dateFormat(LocalDate localDate, String pattern) {
        switch (pattern) {
            case "yyyy-MM-dd":
                return DateTimeFormatter.ofPattern("yyyy-MM-dd");
            case "yyyy.MM.dd":
                return DateTimeFormatter.ofPattern("yyyy.MM.dd");
            default:
                return DateTimeFormatter.ofPattern(pattern);
        }
    }

    /**
     * 현재 날짜를 yyyy-MM-dd 형식으로 반환
     * @return
     */
    public static String localNowDate() {
        LocalDate localDate = LocalDate.now();
        String now = localDate.format(dateFormat(localDate, "yyyy-MM-dd"));
        return now;
    }

    /**
     * 현재 날짜를 format 형식으로 반환
     * @param format
     * @return
     */
    public static String localNowDate(String format) {
        LocalDate localDate = LocalDate.now();
        String now = localDate.format(dateFormat(localDate, format));
        return now;
    }

    /**
     * String 타입의 주어진 날짜를 i년 뒤 날짜로 반환
     * @param str
     * @param i
     * @return
     */
    public static String localDatePlusY(String str, int i) {
        LocalDate localDate = LocalDate.parse(str).plusYears(i);
        String date = localDate.format(dateFormat(localDate, "yyyy-MM-dd"));
        return date;
    }
}
