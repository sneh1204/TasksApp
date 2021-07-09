package com.example.homework02;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    EditText taskNameInput;
    TextView taskDate;
    RadioGroup rGroup;
    DatePickerDialog picker;
    Calendar cldr;
    final public static String CREATE_KEY = "CreateTask";
    final public static String DELETE_KEY = "DeleteTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle(R.string.task_info_label);

        taskNameInput = findViewById(R.id.name_input);
        taskDate = findViewById(R.id.date_input);
        rGroup = findViewById(R.id.radio_group);
        cldr = Calendar.getInstance();

        setDatePicker();
        createTask();

        findViewById(R.id.cancel_display).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }

    protected void setDatePicker() {
        findViewById(R.id.setdate_button).setOnClickListener(v -> {
            picker = new DatePickerDialog(CreateTaskActivity.this, (view, year, month, dayOfMonth) -> {
                cldr.set(year, month, dayOfMonth);
                taskDate.setText(getString(R.string.date_format, cldr.get(Calendar.MONTH), cldr.get(Calendar.DAY_OF_MONTH), cldr.get(Calendar.YEAR)));
            }, cldr.get(Calendar.YEAR), cldr.get(Calendar.MONTH), cldr.get(Calendar.DAY_OF_MONTH));
            picker.getDatePicker().setMinDate(System.currentTimeMillis());
            picker.show();
        });
    }

    protected void createTask() {
        findViewById(R.id.submit_button).setOnClickListener(v -> {

            String name = taskNameInput.getText().toString();
            String dateCheck = taskDate.getText().toString();
            if(name.isEmpty() || dateCheck.isEmpty()){
                Toast.makeText(getApplicationContext(), R.string.invalid_task_entry, Toast.LENGTH_LONG).show();
                return;
            }

            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(CreateTaskActivity.this);
            confirmDialog.setTitle(R.string.task_confirm)
                    .setPositiveButton(R.string.pos, (dialogInterface, i) -> {

                        Intent intent = new Intent();
                        int rid = rGroup.getCheckedRadioButtonId();
                        String priority;
                        if(rid == R.id.radio_button_low){
                            priority = "Low";
                        }else if(rid == R.id.radio_button_medium){
                            priority = "Medium";
                        }else{
                            priority = "High";
                        }
                        intent.putExtra(CREATE_KEY, new Task(name, cldr, priority));
                        setResult(RESULT_OK, intent);
                        finish();

                    }).setNegativeButton(R.string.neg, null);
            confirmDialog.create().show();
        });
    }


}