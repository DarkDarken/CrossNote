package com.example.programmer.app_beta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class TrainingViewActivity extends AppCompatActivity {

    public static final String EXTRA_RESPONSE = "EXTRA_RESPONSE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        String work = getIntent().getStringExtra(TrainingListActivity.EXTRA_WORK);
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(work);

        FloatingActionButton returnButton = (FloatingActionButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingViewActivity.this, TrainingListActivity.class);
                startActivity(intent);
            }
        });
    }
}
