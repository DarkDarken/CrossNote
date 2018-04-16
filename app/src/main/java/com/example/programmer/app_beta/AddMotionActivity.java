package com.example.programmer.app_beta;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddMotionActivity extends AppCompatActivity {

    private EditText repetition, weight;
    private Spinner motionSpinner;
    private TextInputLayout layout;
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
        repetition.setVisibility(View.INVISIBLE);
        layout = (TextInputLayout) findViewById(R.id.TextInputWeight);
        weight = (EditText) findViewById(R.id.weightEdit);
        weight.setVisibility(View.INVISIBLE);
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
                weight.setText("");
                repetition.setText("");
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });

        motionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: case 1: case 3: case 4: case 5: case 6: case 15: case 18: case 26: case 29:
                    case 30: case 32: case 33: case 34: case 35: case 36: case 39: case 45:
                        repetition.setVisibility(View.VISIBLE);
                        weight.setVisibility(View.INVISIBLE);
                        layout.setHint("");
                        break;
                    case 2: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                    case 17: case 19: case 20: case 21: case 22: case 23: case 24: case 25: case 28:
                    case 31: case 37: case 38: case 40: case 41: case 42: case 43: case 44:
                        repetition.setVisibility(View.VISIBLE);
                        weight.setVisibility(View.VISIBLE);
                        //weight.setHint("weight");
                        layout.setHint("weight");
                        break;
                    default:
                        repetition.setVisibility(View.INVISIBLE);
                        weight.setVisibility(View.INVISIBLE);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

    }

    public void AlertDialog(){
        dialog = new AlertDialog.Builder(AddMotionActivity.this, R.style.MyDialogTheme).create();
        dialog.setTitle("Did you finish adding movment?");

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
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
