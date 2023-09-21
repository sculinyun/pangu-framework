package cn.com.hbscjt.app.framework.common.util;

import cn.hutool.core.date.DateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 *
 * @author 芋道源码
 */
public class DateUtils {


    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    /**
     * 秒转换成毫秒
     */
    public static final long SECOND_MILLIS = 1000;

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String CN_FULL_DATE = "yyyy年MM月dd日";
    public static final String CN_FULL_DATE_BIAS = "yyyy年MM月dd日 HH:mm";
    public static final String CN_HALF_DATE = "yyyy年MM月";
    public static final String CN_FULL_DATE_AND_TIME = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FULL_DATE_AND_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_DATE_AND_TIME_NO_LINK = "yyyyMMddHHmmss";
    public static final String FULL_DATE = "yyyy-MM-dd";
    public static final String FULL_DATE_MONTH = "yyyy-MM";
    public static final String FULL_DATE_HOUR = "yyyy-MM-dd HH";
    public static final String FULL_DATE_HOUR_BIAS = "yyyy/MM/dd";
    public static final String FULL_DATE_TIME_BIAS = "yyyy/MM/dd HH:mm";
    public static final String FULL_DATE_MINUTE = "yyyy-MM-dd HH:mm";

    public static final String SHORT_TIME = "HH:mm";
    public static final String SHORT_TIME_NO_COLON = "HHmm";
    public static final String SHORT_TIME_WITH_SEC = "HH:mm:ss";
    public static final String FULL_DATE_AND_TIME_MIC = "yyyy-MM-dd HH:mm:ss:sss";

    public static final String FULL_DATE_AND_TIME_STRING = "yyyyMMddHHmmss";

    public static final String NO_Q_DATE = "yyyyMMdd";

    public static final String ONLY_TIME = "HHmmss";


    public static Date addTime(Duration duration) {
        return new Date(System.currentTimeMillis() + duration.toMillis());
    }

    public static boolean isExpired(Date time) {
        return System.currentTimeMillis() > time.getTime();
    }

    public static long diff(Date endTime, Date startTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 创建指定时间
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day) {
        return buildTime(year, mouth, day, 0, 0, 0);
    }

    /**
     * 创建指定时间
     *
     * @param year   年
     * @param mouth  月
     * @param day    日
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day,
                                 int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0); // 一般情况下，都是 0 毫秒
        return calendar.getTime();
    }

    public static Date max(Date a, Date b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static boolean beforeNow(Date date) {
        return date.getTime() < System.currentTimeMillis();
    }

    public static boolean afterNow(Date date) {
        return date.getTime() >= System.currentTimeMillis();
    }


    /**
     * * @Desc  : 起/止时间介于当前时间
     * * @Author: xb12369
     * * @Email : binxie@hbscjt.com.cn
     * * @Date  : 2022/11/4 11:39
     * * @Params: startDate EndDate
     * * @Return: boolean
     */
    public static boolean betweenNow(String dateFormat, String start, String end) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(dateFormat);
            Date startDate = df.parse(start);
            Date endDate = df.parse(end);
            return beforeNow(startDate) && System.currentTimeMillis() <= endDate.getTime();
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * * @Desc  : 时间格式比较：0#开始时间大于当前时间、1#开始结束介入当前之间、2#结束时间小于当前时间
     * * @Author: xb12369
     * * @Email : binxie@hbscjt.com.cn
     * * @Date  : 2022/11/9 18:42
     * * @Params: dateFormat start end
     * * @Return: int
     */
    public static int beforeAfterNow(String start, String end) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(FULL_DATE);
            Date startDate = df.parse(start);
            Date endDate = df.parse(end);
            Date now = df.parse(df.format(new Date()));
            if (now.compareTo(startDate) >= 0 && now.compareTo(endDate) <= 0) {
                return 1;
            }
            if (now.compareTo(startDate) < 0) {
                return 0;
            }
            if (now.compareTo(endDate) > 0) {
                return 2;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) throws ParseException {
        String dateStr = "2022-11-10";
        SimpleDateFormat df = new SimpleDateFormat(FULL_DATE);
        Date startDate = df.parse(dateStr);
        System.out.println(afterNow(startDate));
        // System.out.println(beforeAfterNow("2022-11-09", "2022-11-09"));
        System.out.println(getDayOfMonth("2023-02"));
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param field  日历字段.<br/>eg:Calendar.MONTH,Calendar.DAY_OF_MONTH,<br/>Calendar.HOUR_OF_DAY等.
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(int field, int amount) {
        return addDate(null, field, amount);
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param date   设置时间
     * @param field  日历字段 例如说，{@link Calendar#DAY_OF_MONTH} 等
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(Date date, int field, int amount) {
        if (amount == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * * @Desc  : 校验是否为时间格式（全部符合则为true）
     * * @Author: xb12369
     * * @Email : binxie@hbscjt.com.cn
     * * @Date  : 2022/11/4 11:13
     * * @Params: dateStr
     * * @Return: boolean
     */
    public static boolean isValidDate(String dateFormat, String... dateStr) {
        final DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        for (int i = 0; i < dateStr.length; i++) {
            try {
                Date parse = sdf.parse(dateStr[i]);
                String format = DateUtil.format(parse, dateFormat);

                if (format.length() != dateFormat.length()) {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * * @Desc  : 根据出生年月日计算年龄
     * * @Author: xb12369
     * * @Email : binxie@hbscjt.com.cn
     * * @Date  : 2022/11/20 13:48
     * * @Params: birthday
     * * @Return: int
     */
    public static int getAgeByBirth(Date birthday) {

        //获取当前时间
        Calendar cal = Calendar.getInstance();

        //获取出生日期的Calendar对象
        Calendar bir = Calendar.getInstance();
        bir.setTime(birthday);
        //如果出生日期大于当前日期，则返回0
        if (cal.before(birthday)) {
            return 0;
        }
        //取出当前年月日
        int nowYear = cal.get(Calendar.YEAR);
        int nowMonth = cal.get(Calendar.MONTH);
        int nowDay = cal.get(Calendar.DAY_OF_MONTH);

        //取出出生日期的年月日
        int birthYear = bir.get(Calendar.YEAR);
        int birthMonth = bir.get(Calendar.MONTH);
        int birthDay = bir.get(Calendar.DAY_OF_MONTH);

        //计算年份
        int age = nowYear - birthYear;

        //计算月份和日，看看是否大于当前月日，如果小于则减去一岁
        if (nowMonth < birthMonth || (nowMonth == birthMonth && nowDay < birthDay)) {
            age--;
        }
        return age;
    }

    /**
     * * @Desc  : parse_yyyyMM 方法（到月）
     * * @Author: xb12369
     * * @Email : binxie@hbscjt.com.cn
     * * @Date  : 2022/11/24 11:32
     * * @Params: time
     * * @Return: java.lang.String
    */
    public static String format_time(LocalDateTime time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(FULL_DATE_MONTH);
        return df.format(time);
    }

    public static int getDayOfMonth(String month) {
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(FULL_DATE_MONTH);
    		Calendar calendar = Calendar.getInstance(Locale.CHINA);
    		calendar.setTime(sdf.parse(month));
    		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    	} catch (ParseException e) {
            return 0;
        }
    }

}
