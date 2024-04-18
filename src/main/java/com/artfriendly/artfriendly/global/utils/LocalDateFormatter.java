package com.artfriendly.artfriendly.global.utils;

import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateFormatter {
    public static String convertToString(LocalDate localDate, String format){
        DateTimeFormatter formatter;
        try{
            formatter = DateTimeFormatter.ofPattern(format);
        }
        catch (Exception e) {
            throw new BusinessException(ErrorCode.WRONG_DATE_FORMAT);
        }

        return localDate.format(formatter);
    }
    public static LocalDate converToLocalDate(String dateStr, String format) {
        DateTimeFormatter formatter;
        try{
            formatter = DateTimeFormatter.ofPattern(format);
        }
        catch (Exception e) {
            throw new BusinessException(ErrorCode.WRONG_DATE_FORMAT);
        }

        return LocalDate.parse(dateStr, formatter);
    }
}
