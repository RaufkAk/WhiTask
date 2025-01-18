package com.raufkutayakyildiz.myapplication.Pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.raufkutayakyildiz.myapplication.R;
import com.raufkutayakyildiz.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Binding Bağlayaıp İstediğim Viewe Rahatça Erişmem İçin
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view1 = binding.getRoot();
        setContentView(view1);
        //Kayıt Oluşturabilmek İçin
        auth = FirebaseAuth.getInstance();

        //Kaydı Olan Kişiyi Doğrudan Ana Sayfaya Götürmek İçin
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this, TodayTaskPage.class);
            startActivity(intent);
            finish();
        }
    }
    public void signInButton(View view){
        //Kullanıcının Mail / Password ünü Almak İçin
        String eMail = binding.EmailText.getText().toString();
        String password = binding.PasswordText.getText().toString();
        //İF---->Boş Bırakıldığında Hata Mesajı Çıkartmak İçin
        //Toast.makeText(kaynak,mesaj içeriği,süre).show
        if(eMail.isEmpty() || (password.isEmpty())){
            Toast.makeText(MainActivity.this,"Enter Email And Password",Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(eMail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this, TodayTaskPage.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void signUpButton(View view){
        String eMail  = binding.EmailText.getText().toString();
        String password = binding.PasswordText.getText().toString();
        if(eMail.isEmpty() || (password.isEmpty())){
            Toast.makeText(MainActivity.this,"Enter Email And Password",Toast.LENGTH_LONG).show();
        }else{
             auth.createUserWithEmailAndPassword(eMail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                 @Override
                 public void onSuccess(AuthResult authResult) {
                     Intent intent = new Intent(MainActivity.this, PphotosPage.class);
                     startActivity(intent);
                     finish();

                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                 }
             });
        }
    }
}