package myApp.michal.crossNote.Activites.EmomTimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Adapters.ListItemStoperAdapter;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Databases.DbHelper;

public class LoaderEmomListViewActivity extends BaseActivity {

    public static final String POSITION_LOAD = "POSITION_LOAD";
    public static final String IS_LOADED = "IS_LOADED";

    private ArrayList<TrainingBuilder> tempList;
    private List<Integer> ids;
    private DbHelper dbHelper;
    private RelativeLayout relativeLayout;
    private ListView trainingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_emom_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolbarAndWindow(toolbar, getString(R.string.title_activity_emom_timer_loader));
        initGUI();
        setDrawerLayoutBackground(dbHelper.getAll(), relativeLayout);
        ListTrainingFilter();
        setDrawerLayoutBackground(tempList, relativeLayout);
        ListItemStoperAdapter adapter = new ListItemStoperAdapter(this, tempList);
        trainingListView.setAdapter(adapter);

        trainingListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(LoaderEmomListViewActivity.this, EmomTimerActivity.class);
            int pos = getIds().get(position);
            String POS = Integer.toString(pos);
            intent.putExtra(POSITION_LOAD,POS);
            intent.putExtra(IS_LOADED,true);
            startActivity(intent);
        });
    }

    @Override
    public void initGUI() {
        dbHelper = new DbHelper(this);
        relativeLayout = findViewById(R.id.emptyLayout);
        trainingListView = findViewById(R.id.listLoader);
        ids = new ArrayList<>();
    }

    public void ListTrainingFilter(){
        tempList = new ArrayList<>();
        for(int i = 0; i < dbHelper.getAll().size(); i++){
            if(dbHelper.getAll().get(i).getEWorkoutNames() == EWorkoutNames.EMOM){
                tempList.add(dbHelper.getAll().get(i));
                ids.add(i);
            }
        }
    }

    public List<Integer> getIds(){
        return ids;
        }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(LoaderEmomListViewActivity.this, EmomTimerActivity.class);
        startActivity(intent);
    }
}

