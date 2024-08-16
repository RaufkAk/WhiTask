package com.raufkutayakyildiz.myapplication.Model;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Date {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getCurrentDate() {
        // Bugünün tarihini al
        LocalDate today = LocalDate.now();

        // Tarih formatını belirle
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM",new Locale("tr"));

        // Tarihi formatla
        String formattedDate = today.format(dateFormatter);

        // Haftanın gününü Türkçe olarak al
        String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("tr"));

        // Sonuçları birleştir
        return formattedDate + " " + dayOfWeek;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getCurrentTimeInMillis() {
        // Şu anki tarihi ve saati al
        LocalDateTime now = LocalDateTime.now();

        // LocalDateTime'ı Instant'a dönüştür
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();

        // Instant'ı long türünde milisaniyeye dönüştür
        return instant.toEpochMilli();
    }

}
