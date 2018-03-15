package com.example.programmer.app_beta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingListActivity extends AppCompatActivity {

    public static final String EXTRA_WORK = "EXTRA_WORK";

    private ListView trainingListView;
    ExpenseListAdapter adapter = new ExpenseListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);


        trainingListView = (ListView) findViewById(R.id.listView);
        trainingListView.setAdapter(adapter);

        trainingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(TrainingListActivity.this, TrainingViewActivity.class);
                intent.putExtra(EXTRA_WORK, adapter.getItem(position).getWork());
                startActivity(intent);
            }
        });

        FloatingActionButton returnButton = (FloatingActionButton) findViewById(R.id.returnButtonList);
        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingListActivity.this, MainActivity.class);
                startActivity(intent);
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

    private class ExpenseListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return TrainingDatabase.getTrainings().size();
        }

        @Override
        public Training getItem(int position) {
            return TrainingDatabase.getTrainings().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_training, null);
            }

            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView time = (TextView) convertView.findViewById(R.id.timeCap);
            TextView category = (TextView) convertView.findViewById(R.id.category);
            Training item = getItem(position);
            date.setText(item.getDate());

            String nTime = item.getTime() + " " + "min";

            time.setText(nTime);
            category.setText(item.getCategory().getName());

            return convertView;
        }
    }
    protected void removeItemFromList(int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(TrainingListActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TOD O Auto-generated method stub

                // main code on after clicking yes
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.about_menu){
            Toast.makeText(TrainingListActivity.this, "About menu was cliked", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.setting_menu){
            Toast.makeText(TrainingListActivity.this, "Setting menu was cliked", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
