package com.example.programmer.app_beta;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class TrainingAddActivity extends AppCompatActivity {

    private TextView dateEditText;
    private EditText timeEditText;
    private Spinner categorySpinner;
    private EditText workEditText;
    private TextInputLayout layout;
    private CategoryAdapter categoryAdapter;
    private TrainingCategory trainingCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        dateEditText = (TextView) findViewById(R.id.dataText);
        timeEditText = (EditText) findViewById(R.id.setTimeEdit);
        categorySpinner = (Spinner) findViewById(R.id.typeWodSpinner);
        layout = (TextInputLayout) findViewById(R.id.textInputTime);

        categoryAdapter = new CategoryAdapter(this, trainingCategory);
        categorySpinner.setAdapter(categoryAdapter);

        setCurrentDate(dateEditText);

        FloatingActionButton addTrainingButton = (FloatingActionButton) findViewById(R.id.add_expense);
        addTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTraining();
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 2) {
                    timeEditText.setHint("rounds");
                    layout.setHint("rounds");
                    timeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                }else if(i == 3){
                    layout.setHint("bemchmark");
                    timeEditText.setHint("bemchmark");
                    timeEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                }else{
                    layout.setHint("time");
                    timeEditText.setHint("time");
                    timeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addNewTraining() {
        if( !timeEditText.getText().toString().equals("") && timeEditText.getText().toString().length() > 0 )
        {
            ArrayList<Motion> motionList = new ArrayList<Motion>();

            String date = dateEditText.getText().toString();

            String time = timeEditText.getText().toString();

            TrainingCategory category = (TrainingCategory) categorySpinner.getSelectedItem();
            boolean box = false;


            Training training = new Training(date, time, category, motionList, box);
            TrainingDatabase.addTraining(training);

            Intent intent = new Intent(TrainingAddActivity.this, AddMotionActivity.class);
            startActivity(intent);
            //finish();
        } else {
            Toast.makeText(TrainingAddActivity.this, "Enter the number greater than zero",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "dataPicker");
    }

    public void setCurrentDate(TextView textView){
        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        textView.setText(new StringBuilder().append(dd).append("/").append(mm+1).append("/").append(yy));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.about_menu){
            Intent intent = new Intent(TrainingAddActivity.this, AboutPopUpActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.add_action){
            addNewTraining();
        }
        return super.onOptionsItemSelected(item);
    }



}
