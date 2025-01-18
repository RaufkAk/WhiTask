package com.raufkutayakyildiz.myapplication.Pages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.raufkutayakyildiz.myapplication.Adapter.TaskAdapter;
import com.raufkutayakyildiz.myapplication.Model.Date;
import com.raufkutayakyildiz.myapplication.Model.Task;
import com.raufkutayakyildiz.myapplication.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

    public class TodayTaskPage extends AppCompatActivity {
        private FirebaseAuth auth;
        private RecyclerView recyclerView;
        private List<Task> taskList;
        private TaskAdapter taskAdapter;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_today_task_page);
            auth = FirebaseAuth.getInstance();

            String formattedDate = Date.getCurrentDate();
            TextView todayDate = findViewById(R.id.DateText);
            todayDate.setText(formattedDate);

            recyclerView = findViewById(R.id.recyclerView);
            taskList = new ArrayList<>();
            taskAdapter = new TaskAdapter(taskList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(taskAdapter);
        }
        public void LogOut(View view) {
            auth.signOut();
            Intent intentToSignout = new Intent(TodayTaskPage.this, MainActivity.class);
            startActivity(intentToSignout);
            finish();
        }

        public void SettingsButton(View view) {
            Intent intentToSettings = new Intent(TodayTaskPage.this, SettingsPage.class);
            startActivity(intentToSettings);
            finish();
        }
        public void AddTaskButton(View view) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_task_manager, null);
            final EditText taskNameEditText = dialogView.findViewById(R.id.taskNameEditText);
            final EditText taskTimeEditText = dialogView.findViewById(R.id.taskTimeEditText);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Task Manager")
                    .setView(dialogView)
                    .setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String taskName = taskNameEditText.getText().toString();
                            String taskTime = taskTimeEditText.getText().toString();

                            if (!taskName.isEmpty() && !taskTime.isEmpty()) {
                                Timestamp currentTimestamp = new Timestamp(new java.util.Date());
                                Task newTask = new Task(taskName, taskTime, currentTimestamp);
                                taskAdapter.addTask(newTask);
                                taskAdapter.notifyDataSetChanged(); // Tüm veri setini yeniden yükleyin

                            } else {
                                Toast.makeText(getApplicationContext(), "Both fields must be filled!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        public void CalendarButton (View view){
            Intent intentToCalendar = new Intent(TodayTaskPage.this, CalendarPage.class);
            startActivity(intentToCalendar);
            finish();

        }
        public void ProfileButton(View view){
            Intent intentToProfile = new Intent(TodayTaskPage.this, ProfilePage.class);
            startActivity(intentToProfile);
            finish();
        }
    }
