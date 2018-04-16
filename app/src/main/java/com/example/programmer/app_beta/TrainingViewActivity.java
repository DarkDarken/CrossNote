package com.example.programmer.app_beta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.programmer.app_beta.TrainingListActivity.POSITION;

public class TrainingViewActivity extends AppCompatActivity {

    public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";

    TextView textView;
    AlertDialog dialog;
    private View alertView;
    ListMotionAdapter adapter;
    ArrayList<Motion> list;
    FloatingActionButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        String positionString = getIntent().getStringExtra(TrainingListActivity.POSITION);

        final int position = Integer.parseInt(positionString);


        list = TrainingDatabase.getTrainings().get(position).getMotion();

        addButton = (FloatingActionButton) findViewById(R.id.addMotionView);


        ArrayList<Motion> motion = TrainingDatabase.getTrainings().get(position).getMotion();

        final ListView listView = (ListView) findViewById(R.id.listViewItems);

        adapter = new ListMotionAdapter(this, TrainingDatabase.getTrainings().get(position).getMotion());

        listView.setAdapter(adapter);


        String category = TrainingDatabase.getTrainings().get(position).getCategory().getName();
        String time = TrainingDatabase.getTrainings().get(position).getTime();
        String timeString;
        if(category == "RFT"){
            if(time == "1") {
                timeString = time + " " + "round";
            } else {
                timeString = time + " " + "rounds";
            }
        } else if(category == "Benchmark") {
            timeString = time + "";
        } else{
            timeString = time + " " + "min";
        }
        final TextView textTime = (TextView) findViewById(R.id.textTime);
        final TextView textCategory = (TextView) findViewById(R.id.textCategory);
        textTime.setText(timeString);
        textCategory.setText(category);


        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        alertView = inflater.inflate(R.layout.custom_alert_layout, null);

        dialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();
        final Spinner mSpinner= (Spinner) alertView.findViewById(R.id.categoryMotion);
        final EditText mRep = (EditText) alertView.findViewById(R.id.repEdit);
        final EditText mWe = (EditText) alertView.findViewById(R.id.weightEdite);


        final ArrayAdapter<MotionCategory> mAdapter = new ArrayAdapter<MotionCategory>(
                TrainingViewActivity.this, android.R.layout.simple_spinner_item, MotionCategory.values());
        mSpinner.setAdapter(mAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int element, long l) {
                dialog.setTitle("Edit the training");
                dialog.setView(alertView);
                mRep.setText(TrainingDatabase.getTrainings().get(position).getMotion().get(element).getRepetition());
                mWe.setText(TrainingDatabase.getTrainings().get(position).getMotion().get(element).getWeight());
                mSpinner.setSelection(mAdapter.getPosition(TrainingDatabase.getTrainings().get(position).getMotion().get(element).getMotionCategory()));
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       list.remove(element);

                       String rep = mRep.getText().toString();
                       MotionCategory category = (MotionCategory) mSpinner.getSelectedItem();
                       String mass = mWe.getText().toString();

                        Motion motion = new Motion(rep, category, mass);

                        list.add(element, motion);
                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int element, long l) {
                removeItemFromList(element);
                return true;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Add new motion");
                dialog.setView(alertView);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String rep = mRep.getText().toString();
                        MotionCategory category = (MotionCategory) mSpinner.getSelectedItem();
                        String mass = mWe.getText().toString();

                        Motion motion = new Motion(rep, category, mass);

                        list.add(motion);
                        adapter.notifyDataSetChanged();
                        adapter.notifyDataSetInvalidated();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(TrainingViewActivity.this, TrainingListActivity.class);
        startActivity(intent);
        return;
    }
    protected void removeItemFromList(int element) {
        final int deletePosition = element;

        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(TrainingViewActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                list.remove(deletePosition);

                adapter.notifyDataSetChanged();
                adapter.notifyDataSetInvalidated();

            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();
    }
}
