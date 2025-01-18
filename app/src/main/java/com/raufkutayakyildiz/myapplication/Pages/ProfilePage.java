package com.raufkutayakyildiz.myapplication.Pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raufkutayakyildiz.myapplication.R;

public class ProfilePage extends AppCompatActivity {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);


        String name = sharedPreferences.getString("name", "N/A");
        String surname = sharedPreferences.getString("surname", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String phone = sharedPreferences.getString("phone", "N/A");
        String dob = sharedPreferences.getString("dob", "N/A");
        String profilePictureUri = sharedPreferences.getString("profile_picture_uri", null);


        TextView nameTextView = findViewById(R.id.textView5);
        TextView surnameTextView = findViewById(R.id.textView6);
        TextView emailTextView = findViewById(R.id.textView8);
        TextView phoneTextView = findViewById(R.id.textView9);
        TextView dobTextView = findViewById(R.id.textView10);
        ImageView profileImageView = findViewById(R.id.imageView7);

        nameTextView.setText(name);
        surnameTextView.setText(surname);
        emailTextView.setText(email);
        phoneTextView.setText(phone);
        dobTextView.setText(dob);

        // Görseli yükle ve hata durumunda varsayılan görseli göster
        if (profilePictureUri != null) {
            try {
                Uri imageUri = Uri.parse(profilePictureUri);
                profileImageView.setImageURI(imageUri);

                // URI'yi Logcat'e yazdır
                Log.d("ProfilePage", "Loaded URI: " + imageUri.toString());
            } catch (Exception e) {
                e.printStackTrace();
                profileImageView.setImageResource(R.drawable.download); // Varsayılan görsel
            }
        } else {
            profileImageView.setImageResource(R.drawable.download);
            Log.d("ProfilePage", "No URI found. Showing default picture.");
        }


        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getX() < e1.getX()) { // Sağdan sola kaydırma hareketi
                    Intent intent = new Intent(ProfilePage.this, TodayTaskPage.class);
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
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
}