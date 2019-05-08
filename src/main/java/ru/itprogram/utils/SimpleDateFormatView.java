package ru.itprogram.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleDateFormatView {
    public static void dataTimePrint() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = null;

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        System.out.println("ENGLISH: " + simpleDateFormat.format(date));

        simpleDateFormat = new SimpleDateFormat("yyyy-dd-MM", Locale.CANADA);
        System.out.println("CANADA: " + simpleDateFormat.format(date));

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        System.out.println("CHINA: " + simpleDateFormat.format(date));
    }
}
