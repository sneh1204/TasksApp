package com.example.homework02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Homework 02 - Spring 2021
 * MainActivity.java
 * Sneh Jain
 * Ivy Pham
 */

public class MainActivity extends AppCompatActivity {

    TextView countTasks, upcomingTask;
    private int countTask;
    public ArrayList<Task> tasksList;
    private String[] taskTitles;
    final public static int REQ_CODE = 999, DISPLAY_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_1);
        tasksList = new ArrayList<>();
        taskTitles = new String[0];

        taskList();
        upcomingTask();

        findViewById(R.id.view_task_button).setOnClickListener(v -> {
            AlertDialog.Builder viewTask = new AlertDialog.Builder(MainActivity.this);
            viewTask.setTitle(R.string.alert_select_task)
                    .setItems(taskTitles, (dialog, which) -> {
                        Intent intent = new Intent(MainActivity.this, DisplayTaskActivity.class);
                        intent.putExtra(CreateTaskActivity.CREATE_KEY, tasksList.get(which));
                        startActivityForResult(intent, DISPLAY_CODE);
                    });
            viewTask.create().show();
        });

        findViewById(R.id.creat_task_button).setOnClickListener(v -> {
            Intent viewTask = new Intent(MainActivity.this, CreateTaskActivity.class);
            startActivityForResult(viewTask, REQ_CODE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskList();
        upcomingTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra(CreateTaskActivity.CREATE_KEY)) {
                    Task task = (Task) data.getSerializableExtra(CreateTaskActivity.CREATE_KEY);
                    tasksList.add(task);
                }
            }
        } else if (requestCode == DISPLAY_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra(CreateTaskActivity.DELETE_KEY)) {
                    Task task = (Task) data.getSerializableExtra(CreateTaskActivity.DELETE_KEY);
                    tasksList.remove(task);
                }
            }
        }
    }

    protected void taskList() {
        countTask = tasksList.size();
        taskTitles = new String[countTask];
        for (int i = 0; i < countTask; i++) {
            taskTitles[i] = tasksList.get(i).taskName;
        }
        countTasks = findViewById(R.id.count_tasks);
        countTasks.setText(getString(R.string.count_task, countTask));
    }

    protected void upcomingTask() {
        upcomingTask = findViewById(R.id.upcoming_task);
        if (countTask == 0) {
            upcomingTask.setText(R.string.none);
        } else {
            ArrayList<Task> copy = new ArrayList(tasksList);
            Collections.sort(copy);
            Task upcoming = copy.get(0);
            upcomingTask.setText(getString(R.string.upcoming_task, upcoming.taskName, getString(R.string.date_format, upcoming.taskDate.get(Calendar.MONTH), upcoming.taskDate.get(Calendar.DAY_OF_MONTH), upcoming.taskDate.get(Calendar.YEAR)), upcoming.priority));
        }
    }

}
