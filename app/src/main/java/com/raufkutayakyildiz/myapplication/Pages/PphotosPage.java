package com.raufkutayakyildiz.myapplication.Pages;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.raufkutayakyildiz.myapplication.R;

public class PphotosPage extends AppCompatActivity {

    private ActivityResultLauncher<String> permissionLauncher;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pphotos_page);
        registerLauncher();
    }

    public void imageViewClicked(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Give Permission", v -> permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)).show();
            } else {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGallery);
        }
    }

    // Görsel URI'yi kaydetme işlemini güncelledim
    private void registerLauncher() {
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            } else {
                Snackbar.make(findViewById(R.id.main), "Permission denied", Snackbar.LENGTH_SHORT).show();
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    imageUri = data.getData();
                    ImageView imageView = findViewById(R.id.imageView11);
                    imageView.setImageURI(imageUri);

                    // URI'yi SharedPreferences'e kaydet
                    SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile_picture_uri", imageUri.toString());
                    editor.apply();

                    // URI'nin doğru kaydedildiğini Logcat'te görmek için
                    Log.d("PphotosPage", "Saved URI: " + imageUri.toString());

                    // 3 saniye bekle ve RegisterPage'e geç
                    new Handler().postDelayed(() -> {
                        Intent intentToRegisterPage = new Intent(PphotosPage.this, RegisterPage.class);
                        startActivity(intentToRegisterPage);
                        finish();
                    }, 3000);
                }
            }
        });
    }
}