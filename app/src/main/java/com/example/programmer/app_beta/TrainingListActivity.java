package com.example.programmer.app_beta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Locale;

public class TrainingListActivity extends AppCompatActivity {

    public static final String POSITION = "POSITION";

    private ListView trainingListView;
    private EditText searchEdit;
    ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);

        trainingListView = (ListView) findViewById(R.id.listView);
        adapter = new ListItemAdapter(this, TrainingDatabase.getTrainings());

        trainingListView.setAdapter(adapter);

        searchEdit = (EditText) findViewById(R.id.editSearch);


        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchEdit.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });


        trainingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(TrainingListActivity.this, TrainingViewActivity.class);
                    String POS = Integer.toString(position);
                    intent.putExtra(POSITION, POS);
                    startActivity(intent);
            }
        });

        FloatingActionButton deleteButton = (FloatingActionButton) findViewById(R.id.deleteItemsButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItemsFromList();
            }
        });


      trainingListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                removeItemFromList(position);
                return true;
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        trainingListView.invalidateViews();
    }

    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(TrainingListActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                TrainingDatabase.delateItem(deletePosition);
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

    protected void removeItemsFromList() {

        AlertDialog.Builder alert = new AlertDialog.Builder(TrainingListActivity.this);

        int size = TrainingDatabase.getTrainings().size();
        int count = 0;
        for(int i=0; i<size; i++){
            Training training = TrainingDatabase.getTrainings().get(i);

            if(training.isBox()){
                count++;
            }
        }

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete " + count + " item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int size = TrainingDatabase.getTrainings().size();
                for(int i=0; i<size; i++){

                    Training training = TrainingDatabase.getTrainings().get(i);

                    if(training.isBox()){
                        TrainingDatabase.delateItem(i);
                        i--;
                        size = TrainingDatabase.getTrainings().size();
                    }

                }
                adapter.notifyDataSetChanged();
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(TrainingListActivity.this, MainActivity.class);
        startActivity(intent);
        return;
    }
    }
