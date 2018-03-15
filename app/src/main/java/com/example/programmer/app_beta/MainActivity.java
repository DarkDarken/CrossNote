package com.example.programmer.app_beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
    }

    public void addWorkOutClick(View view){
        Intent intent = new Intent(MainActivity.this, TrainingAddActivity.class);
        startActivity(intent);
    }

    public void myHistory(View view){
        Intent intent = new Intent(MainActivity.this, TrainingListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.about_menu){
            Toast.makeText(MainActivity.this, "About menu was cliked", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.setting_menu){
            Toast.makeText(MainActivity.this, "Setting menu was cliked", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}

