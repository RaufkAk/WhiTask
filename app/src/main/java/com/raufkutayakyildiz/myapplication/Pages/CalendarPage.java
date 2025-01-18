package com.raufkutayakyildiz.myapplication.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.raufkutayakyildiz.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarPage extends AppCompatActivity {
    private GestureDetector gestureDetector;
    private TextView taskList;
    private TextView taskTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        CalendarView calendarView = findViewById(R.id.calendarView);
        taskList = findViewById(R.id.taskList);
        taskTitle = findViewById(R.id.taskTitle);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = formatSelectedDate(year, month, dayOfMonth);
            taskTitle.setText(selectedDate);
            loadTasksForDate(selectedDate);
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getX() < e1.getX()) {
                    Intent intent = new Intent(CalendarPage.this, TodayTaskPage.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    // Seçilen tarih için Firestore'dan görevleri yükler
    private void loadTasksForDate(String date) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference tasksRef = db.collection(date);

        tasksRef.orderBy("timestamp")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        StringBuilder taskString = new StringBuilder();
                        int index = 1;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String taskName = document.getString("name");
                            String taskTime = document.getString("time");


                            taskString.append(index).append(". Görev: ").append(taskName)
                                    .append(" | Saat: ").append(taskTime).append("\n");
                            index++;
                        }
                        taskList.setText(taskString.toString());
                    } else if (task.isSuccessful() && task.getResult().isEmpty()) {
                        taskList.setText("Bu tarihte görev bulunamadı.");
                    } else {
                        taskList.setText("Görevler yüklenirken bir hata oluştu.");
                    }
                });
    }
    private String formatSelectedDate(int year, int month, int dayOfMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM EEEE", Locale.forLanguageTag("tr"));

        Date date = new Date(year - 1900, month, dayOfMonth);
        return sdf.format(date);
    }
}