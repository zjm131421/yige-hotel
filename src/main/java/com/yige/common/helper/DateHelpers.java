package com.yige.common.helper;

import com.yige.common.config.AppConfig;
import com.yige.common.utils.SpringContextHolder;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Date;

/**
 * @author zoujm
 * @since 2018/12/1 14:05
 */
public class DateHelpers {

    private static long presetSystemTime = 0;

    public static final LocalDate EMPTY_DATE = LocalDate.ofEpochDay(0);
    public static final LocalTime EMPTY_TIME = LocalTime.of(0, 0, 0);
    public static final LocalDateTime EMPTY_DATE_TIME = LocalDateTime.of(EMPTY_DATE, EMPTY_TIME);

    public static final DateTimeFormatter DATE_TIME_NO_SPACE = new DateTimeFormatterBuilder().appendPattern("yyyyMMddHHmmss").toFormatter();
    public static final DateTimeFormatter MYSQL_DATE_TIME = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();

    private static final DateTimeFormatter[] COMMON_DATE_FORMATS = new DateTimeFormatter[]{
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DATE_TIME_NO_SPACE,
            new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ HH:mm:ss]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter(),

    };

    public static long currentTimeInMilliseconds() {
        if (DateHelpers.presetSystemTime > 0) {
            return DateHelpers.presetSystemTime;
        }
        return System.currentTimeMillis();
    }

    public static long currentTimestamp() {
        return (currentTimeInMilliseconds() / 1000);
    }

    public static LocalDateTime dateTimeOf(long timestamp) {
        return dateTimeOf(timestamp, 0);
    }

    public static LocalDateTime dateTimeOf(long timestamp, int nanoOfSeconds) {
        AppConfig config = SpringContextHolder.getApplicationContext().getBean(AppConfig.class);
        return LocalDateTime.ofEpochSecond(timestamp, nanoOfSeconds, config.getTimezone());
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(23, 59, 59);
    }

    public static LocalDateTime now() {
        return dateTimeOf(currentTimestamp());
    }

    public static LocalDate today() {
        return now().toLocalDate();
    }

    public static int weekOfYear(LocalDate date) {
        return date.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfWeekBasedYear());
    }

    public static LocalDate parseDate(String dateString) {
        return parseDateTime(dateString).toLocalDate();
    }

    public static LocalTime parseTime(String dateString) {
        dateString = StringHelpers.limitLength(dateString.trim(), 8);
        try {
            return LocalTime.parse(dateString);
        }
        catch(RuntimeException ignored){
            return EMPTY_TIME;
        }
    }

    public static LocalDateTime parseDateTime(String dateString) {
        dateString = StringHelpers.limitLength(dateString.trim(), 19);
        return parseDateTime(dateString, COMMON_DATE_FORMATS);
    }

    public static LocalDateTime parseDateTime(String dateString, DateTimeFormatter[] dateTimeFormatters) {
        for (DateTimeFormatter formatter: dateTimeFormatters) {
            try {
                return LocalDateTime.parse(dateString, formatter);
            }
            catch(RuntimeException ignored) {
            }
        }
        return EMPTY_DATE_TIME;
    }

    public static String getTimeStamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    public static String getSecondTimeStamp(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDate dateToLocalDate(Date date){
        return dateToLocalDateTime(date).toLocalDate();
    }

    public static long localDateTimeToLong(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
