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

    /**
     * @Name getQuarter
     * @Author HuoJiaJin
     * @Description 获得当前季度相关月份
     * @Date 2020/9/16 22:59
     * @Param [month]
     * @return java.util.List<java.lang.Integer>
     **/
    public static List<Integer> getQuarter(int month){
        List<Integer> quarter = Lists.newArrayList();
        if (month >= 1 && month <= 3){
            quarter.add(1);
            quarter.add(2);
            quarter.add(3);
        } else if (month >= 4 && month <= 6){
            quarter.add(4);
            quarter.add(5);
            quarter.add(6);
        } else if (month >= 7 && month <= 9){
            quarter.add(7);
            quarter.add(8);
            quarter.add(9);
        } else if (month >= 10 && month <= 12){
            quarter.add(10);
            quarter.add(11);
            quarter.add(12);
        }
        return quarter;
    }

    /**
     * @Name getThreeMonths
     * @Author HuoJiaJin
     * @Description 获得最近三个月月份
     * @Date 2021/1/29 1:29
     * @Param [month]
     * @return java.util.List<java.lang.Integer>
     **/
    public static List<LocalDate> getThreeMonths(LocalDate date){
        List<LocalDate> threeMonthList = Lists.newArrayList();
        threeMonthList.add(date.minusMonths(2));
        threeMonthList.add(date.minusMonths(1));
        threeMonthList.add(date);
        return threeMonthList;
    }

    /**
     * @Name getWeek
     * @Author HuoJiaJin
     * @Description 获得当前周日期
     * @Date 2020/9/16 23:07
     * @Param []
     * @return java.util.List<java.time.LocalDate>
     **/
    public static List<LocalDate> getWeek(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }
        List<LocalDate> week = Lists.newArrayList();
        for (int i = 0; i < 7; i++) {
            week.add(LocalDate.parse(df.format(calendar.getTime())));
            calendar.add(Calendar.DATE, 1);
        }
        return week;
    }
}
