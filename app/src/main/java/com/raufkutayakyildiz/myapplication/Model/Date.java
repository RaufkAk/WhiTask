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
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM", new Locale("tr"));
        String formattedDate = today.format(dateFormatter);
        String dayOfWeek = today.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("tr"));

        return formattedDate + " " + dayOfWeek;
    }

}
