package com.chrischou.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class CommonUtils {

    private final static Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private static final DateFormat DF_UTC = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss 'UTC'", Locale.ENGLISH);
    private static final DateTimeFormatter DF_ISO = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateFormat DF_GMT = new SimpleDateFormat("MMMM d, yyyy 'at' hh:mm 'GMT'", Locale.ENGLISH);

    private static final SimpleDateFormat DF_GLOBAL_DATE_FOR_DTO = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static Date str2UTC(String dateStr) {
        try {
            return DF_UTC.parse(dateStr);
        } catch (ParseException e) {
            log.error("str2UTC parse error from: [{}].", dateStr, e);
        }
        return null;
    }

    public static Date str2ISO(String dateStr) {
        try {
            LocalDateTime localDate = LocalDateTime.parse(dateStr, DF_ISO);
            Instant instant = localDate.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        } catch (DateTimeParseException e) {
            log.error("str2ISO parse error from: [{}].", dateStr, e);
        }
        return null;
    }

    public static Date str2GMT(String dateStr) {
        try {
            return DF_GMT.parse(dateStr);
        } catch (ParseException e) {
            log.error("str2GMT parse error from: [{}].", dateStr, e);
        }
        return null;
    }

    public static String date2StrForDTO(Date date) {
        return DF_GLOBAL_DATE_FOR_DTO.format(date);
    }

    public static String unicode2String(String unicode) {
        StringBuffer str = new StringBuffer();
        if (unicode.startsWith("&#")) {
            String[] hex = unicode.replace("&#", "").split(";");
            for (String s : hex) {
                try {
                    int data = Integer.parseInt(s, 10);
                    str.append((char) data);
                }
                catch (Exception e) {
                    str.append(s);
                }
            }
        }
        else if (unicode.startsWith("&") && unicode.endsWith(";")) {
            String[] hex = unicode.replace("&", "").split(";");
            for (String s : hex) {
                str.append(s);
            }
        }
        return str.toString();
    }

    public static String collectRequestInvalidErrorsMessage(Errors errors) {
        StringBuffer buffer = new StringBuffer();
        errors.getAllErrors().forEach(e -> buffer.append(e.getDefaultMessage()).append(" "));
        return buffer.toString();
    }

    public static void main(String... args) {
//        System.out.println(unicode2String("&#36;"));
//        System.out.println(unicode2String("&pound;"));
//        System.out.println(unicode2String("&euro;"));

//        System.out.println(str2UTC("Mar 26, 2022 08:10:00 UTC"));
//        System.out.println(str2ISO("2022-03-26T08:10:00+00:00"));
//        System.out.println(str2GMT("Mar 26, 2022 at 08:10 GMT"));

        System.out.println(Pattern.matches("^\\d{4}\\/\\d{2}\\/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", "1990/03/21 00:23:54"));
    }
}

