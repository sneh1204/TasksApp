package com.example.homework02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DisplayTaskActivity extends AppCompatActivity {

    TextView name, date, priority;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        setTitle(R.string.display_task_label);

        if(getIntent() != null && getIntent().hasExtra(CreateTaskActivity.CREATE_KEY)){
            task = (Task)getIntent().getSerializableExtra(CreateTaskActivity.CREATE_KEY);
            name = findViewById(R.id.name_input);
            date = findViewById(R.id.date_display);
            priority = findViewById(R.id.priority_display);
            name.setText(task.taskName);
            date.setText(getString(R.string.date_format, task.taskDate.get(Calendar.MONTH), task.taskDate.get(Calendar.DAY_OF_MONTH), task.taskDate.get(Calendar.YEAR)));
            priority.setText(task.priority);
        }

        findViewById(R.id.cancel_display).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        findViewById(R.id.delete_display).setOnClickListener(v -> {
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(DisplayTaskActivity.this);
            confirmDialog.setTitle(R.string.task_confirm)
                    .setPositiveButton(R.string.pos, (dialogInterface, i) -> {
                        Intent intent = new Intent();
                        intent.putExtra(CreateTaskActivity.DELETE_KEY, task);
                        setResult(RESULT_OK, intent);
                        finish();
                    }).setNegativeButton(R.string.neg, null);
            confirmDialog.create().show();
        });
    }

}