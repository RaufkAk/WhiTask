package com.raufkutayakyildiz.myapplication.Pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.raufkutayakyildiz.myapplication.R;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
    }
    public void saveButtonClicked(View view) {
        EditText nameEditText = findViewById(R.id.edit_name);
        EditText surnameEditText = findViewById(R.id.edit_surname);
        EditText emailEditText = findViewById(R.id.edit_email);
        EditText phoneEditText = findViewById(R.id.edit_phone);
        EditText dobEditText = findViewById(R.id.edit_dob);

        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String dob = dobEditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("dob", dob);
        editor.apply(); // Verileri kaydet

        Intent intent = new Intent(RegisterPage.this, TodayTaskPage.class);
        startActivity(intent);
        finish();
    }
}