package org.open.code.learning.base.design121.decorator_mode.example03.decorator;

import org.open.code.learning.base.design121.decorator_mode.example03.API;
import org.open.code.learning.base.design121.decorator_mode.example03.APIDecorator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class TimeLimitDecorator extends APIDecorator {
    private int startHour;
    private int endHour;

    public TimeLimitDecorator(API api,int startHour,int endHour) {
        super(api);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public String invoke() throws SecurityException {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek day = DayOfWeek.from(now);

        // 检查是否为工作日
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            throw new SecurityException("权限校验失败：非工作日不允许访问");
        }

        // 检查是否在允许的时间段内
        if (now.getHour() < startHour || now.getHour() >= endHour) {
            throw new SecurityException("权限校验失败：不在允许的访问时间内");
        }
        return super.invoke();
    }
}
