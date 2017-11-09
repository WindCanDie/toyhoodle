package com.toy.cube.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAdd implements Function {
    /**
     * @param time    yyyy-MM-dd
     * @param dateNum
     */
    public String exec(String time, Integer dateNum) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, dateNum);
        return format.format(cal.getTime());
    }

}
