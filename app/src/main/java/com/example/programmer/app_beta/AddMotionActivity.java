package com.example.programmer.app_beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddMotionActivity extends AppCompatActivity {

    private EditText repetition, weight;
    private Spinner motionSpinner;
    private Button button, buttonView;
    private MotionSpinnerCategory categoryAdapter;
    private MotionCategory motionCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motion);


        button = (Button) findViewById(R.id.buttonAddMotion);
        buttonView = (Button) findViewById(R.id.buttonView);
        repetition = (EditText) findViewById(R.id.repEdit);
        weight = (EditText) findViewById(R.id.weightEdit);
        motionSpinner = (Spinner) findViewById(R.id.motionSpinner);

        categoryAdapter = new MotionSpinnerCategory(this, motionCategory);
        motionSpinner.setAdapter(categoryAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rep = repetition.getText().toString();
                MotionCategory motion = (MotionCategory) motionSpinner.getSelectedItem();
                String w = weight.getText().toString();

                int repInt = Integer.valueOf(rep);
                int weightInt = Integer.valueOf(w);

                Motion motion1 = new Motion(repInt, motion, weightInt);

                ArrayList<Motion> motionList = TrainingDatabase.getTrainings().get(0).getMotion();

                motionList.add(motion1);
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddMotionActivity.this, TrainingListActivity.class);
                startActivity(intent);

            }
        });

    }
}
