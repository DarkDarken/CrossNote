package com.example.programmer.app_beta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import static com.example.programmer.app_beta.TrainingListActivity.POSITION;

public class TrainingViewActivity extends AppCompatActivity {

    public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";

    TextView textView;
    AlertDialog dialog;
    EditText editText;
    FloatingActionButton editButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        String positionString = getIntent().getStringExtra(TrainingListActivity.POSITION);

        final int position = Integer.parseInt(positionString);

        String work = getIntent().getStringExtra(TrainingListActivity.EXTRA_WORK);
        final TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(work);

        Toast.makeText(TrainingViewActivity.this, positionString, Toast.LENGTH_SHORT).show();

        dialog = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();
        editText = new EditText(this);
        editButton = (FloatingActionButton) findViewById(R.id.editButton);

        dialog.setTitle("Edit the training");
        dialog.setView(editText);

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Training tran = TrainingDatabase.getTrainings().get(position);
                tran.setWork(editText.getText().toString());
                textView.setText(TrainingDatabase.getTrainings().get(position).getWork());
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText(textView.getText());
                dialog.show();
            }
        });

        FloatingActionButton returnButton = (FloatingActionButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingViewActivity.this, TrainingListActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }
}
