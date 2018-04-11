package com.example.programmer.app_beta;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
    private AlertDialog dialog;
    private FloatingActionButton button, buttonView;
    private MotionSpinnerCategory categoryAdapter;
    private MotionCategory motionCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motion);

        AlertDialog();

        button = (FloatingActionButton) findViewById(R.id.buttonAddMotion);
        buttonView = (FloatingActionButton) findViewById(R.id.buttonView);
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

                Motion motion1 = new Motion(rep, motion, w);

                ArrayList<Motion> motionList = TrainingDatabase.getTrainings().get(0).getMotion();

                motionList.add(motion1);
                Toast.makeText(AddMotionActivity.this, "You add new movment", Toast.LENGTH_SHORT).show();
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });

    }

    public void AlertDialog(){
        dialog = new AlertDialog.Builder(AddMotionActivity.this, R.style.MyDialogTheme).create();
        dialog.setTitle("Did you finish adding movment?");

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(AddMotionActivity.this, TrainingListActivity.class);
                startActivity(intent);
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
    }
}
