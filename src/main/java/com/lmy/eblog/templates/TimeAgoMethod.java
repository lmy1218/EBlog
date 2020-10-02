package com.lmy.eblog.templates;
/**
 * @Project eblog
 * @Package com.lmy.eblog.templates
 * @author Administrator
 * @date 2020/10/2 9:50
 * @version V1.0
 */

import com.lmy.eblog.templates.common.DirectiveHandler;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Lmy
 * @ClassName TimeAgoMethod
 * @Description 自定义时间处理标签
 * @date 2020/10/2 9:50
 **/
@Component
public class TimeAgoMethod extends DirectiveHandler.BaseMethod {
    // 定义时间单位相应的毫秒数
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    // 定义时间后缀
    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
    private static final String ONE_UNKNOWN = "未知";

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        // 获取时间参数
        Date time = getDate(arguments, 0);
        // 处理并返回
        return format(time);
    }

    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String format(Date date) {
        // 校验时间是否为空
        if (null == date) {
            return ONE_UNKNOWN;
        }
        // 将时间转换成毫秒数
        long delta = new Date().getTime() - date.getTime();
        // 判断是否小于一分钟
        if (delta < 1L * ONE_MINUTE) {
            // 将时间装换成秒
            long seconds = toSeconds(delta);
            // 返回秒数加后缀
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }

        if (delta < 60L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    /*----------------- 一系列时间处理方法 -------------------*/

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
