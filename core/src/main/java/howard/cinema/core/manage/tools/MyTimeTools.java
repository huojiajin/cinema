package howard.cinema.core.manage.tools;

import org.apache.commons.compress.utils.Lists;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

/**
 * @ClassName MyTimeTools
 * @Description 时间相关工具
 * @Author HuoJiaJin
 * @Date 2020/6/27 5:44
 * @Version 1.0
 **/
public class MyTimeTools {

    public static String timeToStr(LocalDateTime time){
        return timeToStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String timeToStr(LocalDateTime time, String pattern){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(time);
    }

    public static LocalDateTime strToTime(String timeStr){
        return strToTime(timeStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime strToTime(String timeStr, String pattern){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(timeStr, df);
    }

    public static String dateToStr(LocalDate date, String pattern){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(date);
    }

    public static LocalDate strToDate(String timeStr, String pattern){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(timeStr, df);
    }

    public static LocalDate strToDate(String timeStr){
        return strToDate(timeStr, "yyyy-MM-dd");
    }
}
