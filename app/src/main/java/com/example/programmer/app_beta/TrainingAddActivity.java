package com.example.programmer.app_beta;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class TrainingAddActivity extends AppCompatActivity {

    private TextView dateEditText;
    private EditText timeEditText;
    private Spinner categorySpinner;
    private EditText workEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);



        dateEditText = (TextView) findViewById(R.id.dataText);
        timeEditText = (EditText) findViewById(R.id.setTimeEdit);
        categorySpinner = (Spinner) findViewById(R.id.typeWodSpinner);
        workEditText = (EditText) findViewById(R.id.workSetEdit);

        categorySpinner.setAdapter(new CategoryAdapter());

        setCurrentDate(dateEditText);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.add_expense);

        //Button newTrainingButton = (Button) findViewById(R.id.add_expense);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewExpense();
            }
        });
    }

    public void addNewExpense() {
        int time = 0;
        String work = workEditText.getText().toString();
        if( !timeEditText.getText().toString().equals("") && timeEditText.getText().toString().length() > 0 )
        {
            String date = dateEditText.getText().toString();
            time = Integer.parseInt(timeEditText.getText().toString());
            TrainingCategory category = (TrainingCategory) categorySpinner.getSelectedItem();


            Training training = new Training(date, time, category, work);
            TrainingDatabase.addExpense(training);
            finish();
        } else {
            Toast.makeText(TrainingAddActivity.this, "Enter the number greater than zero",
                    Toast.LENGTH_LONG).show();
        }

    }

    private class CategoryAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return TrainingCategory.values().length;
        }

        @Override
        public TrainingCategory getItem(int position) {
            return TrainingCategory.values()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(android.R.layout.simple_spinner_item, null);
            }

            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setText(getItem(position).getName());

            return convertView;
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
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.about_menu){
            Toast.makeText(TrainingAddActivity.this, "About menu was cliked", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.setting_menu){
            Toast.makeText(TrainingAddActivity.this, "Setting menu was cliked", Toast.LENGTH_SHORT).show();

        }
        if(item.getItemId() == R.id.add_action){
            addNewExpense();
        }
        return super.onOptionsItemSelected(item);
    }



}
