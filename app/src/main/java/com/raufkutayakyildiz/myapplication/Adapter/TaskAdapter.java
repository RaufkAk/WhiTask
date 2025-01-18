package com.raufkutayakyildiz.myapplication.Adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.raufkutayakyildiz.myapplication.Model.Date;
import com.raufkutayakyildiz.myapplication.Model.Task;
import com.raufkutayakyildiz.myapplication.R;
import com.google.firebase.Timestamp;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private ArrayList<String> taskListArr;
    private FirebaseFirestore db;
    private String todayDate;

    // Constructor
    @SuppressLint("NewApi")
    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
        this.taskListArr = new ArrayList<>();
        this.db = FirebaseFirestore.getInstance();
        this.todayDate = Date.getCurrentDate();
        getTasksFromDB();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTask(Task task) {
        Timestamp currentTimestamp = new Timestamp(new java.util.Date());
        task.setTimestamp(currentTimestamp); // Şu anki zaman damgasını ayarla
        taskList.add(task);
        taskListArr.add(task.getName() + " " + task.getTime());
        // Firestore'a eklediğim kısım
        db.collection(todayDate)
                .add(task)
                .addOnSuccessListener(documentReference -> {
                    Log.d("TaskAdapter", "Task added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("TaskAdapter", "Error adding task", e);
                });
    }

    private void getTasksFromDB() {
        db.collection(todayDate)
                .orderBy("timestamp", Query.Direction.ASCENDING) // Zaman damgasına göre sıralama
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    taskList.clear(); // Önceki verileri temizle
                    taskListArr.clear();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Task task = document.toObject(Task.class);
                        if (task != null) {
                            taskList.add(task);
                            taskListArr.add(task.getName() + " " + task.getTime());
                        }
                    }
                    notifyDataSetChanged();
                });
                
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String taskInfo = taskListArr.get(position);
        holder.checkBox.setText(taskInfo);
    }
    @Override
    public int getItemCount() {
        return taskListArr.size();
    }
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}