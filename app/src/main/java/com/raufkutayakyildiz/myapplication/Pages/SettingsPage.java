package com.raufkutayakyildiz.myapplication.Pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.raufkutayakyildiz.myapplication.R;

public class SettingsPage extends AppCompatActivity {

    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        // GestureDetector'u başlat, sadece gerekli metodları override etmek için SimpleOnGestureListener kullan
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // Soldan sağa kaydırma hareketini algıla
                if (e2.getX() >  e1.getX()) {
                    // Ana sayfaya dön
                    Intent intent = new Intent(SettingsPage.this, TodayTaskPage.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }


    //Dokunma Hareketlerini Dinleyip Bunları GestureDetectore Yollamakla Görevli
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}

