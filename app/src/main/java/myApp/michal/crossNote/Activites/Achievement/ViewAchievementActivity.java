package myApp.michal.crossNote.Activites.Achievement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.CustomCoparators;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbPrHelper;
import myApp.michal.crossNote.RecycleAdapters.RecyclerPrViewListAdapter;
import myApp.michal.crossNote.RecyclerItemTouchHelpers.RecyclerItemMainTouchHelper;
import myApp.michal.crossNote.RecyclerItemTouchHelpers.RecyclerItemPrViewTouchHelper;

public class ViewAchievementActivity extends BaseActivity {

    public static final String POSITION = "POSITION";

    private Toolbar toolbar;
    private Window window;
    private RecyclerPrViewListAdapter adapter;
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private DbPrHelper dbPrHelper;
    private int finalPosition;
    private RelativeLayout relativeLayout;
    private ObjectConverter objectConverter = new ObjectConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_achievement);

        String positionString = getIntent().getStringExtra(POSITION);
        int positionFromGraphActivity = getIntent().getIntExtra(GraphActivity.GRAPH_ACTIVITY, -1);
        if(positionString != null) {
            finalPosition = Integer.parseInt(positionString);
        }
        if(positionFromGraphActivity != -1) {
            finalPosition = positionFromGraphActivity;
        }
        dbPrHelper = new DbPrHelper(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(dbPrHelper.getAllPr().get(finalPosition).getMotionCategory().name().replace("_", " "));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.toolbarColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        AchievementBuilder achievementBuilder = dbPrHelper.getAllPr().get(finalPosition);
        List<PrRecordBuilder> records = achievementBuilder.getPrRecordBuilderList();

        List<PrRecordBuilder> sortedRecords = new ArrayList<>(records);

        Collections.sort(sortedRecords, new CustomCoparators());

        window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        recyclerView = findViewById(R.id.RCViewAchievementView);
        relativeLayout = findViewById(R.id.emptyLayout);

        setDrawerLayoutBackground(dbPrHelper.getAllPr().get(finalPosition).getPrRecordBuilderList(), relativeLayout);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);


        FloatingActionButton addRecordButton = findViewById(R.id.add_achievement_button);
        addRecordButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAchievementActivity.this, AddRecordActivity.class);
            String positionForAddRecordActivity = Integer.toString(finalPosition);
            intent.putExtra(POSITION, positionForAddRecordActivity);
            startActivity(intent);
        });

        adapter = new RecyclerPrViewListAdapter(this, sortedRecords, finalPosition);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemPrViewTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, listenerOnSwipe);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public void initGUI() {}

    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe =
            new RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe() {
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
            if (viewHolder instanceof RecyclerPrViewListAdapter.MyViewHolder) {
                final AchievementBuilder achievementBuilder = dbPrHelper.getAllPr().get(finalPosition);

                List<PrRecordBuilder> list = dbPrHelper.getAllPr().get(finalPosition).getPrRecordBuilderList();

                final int deletedIndex = viewHolder.getAdapterPosition();

                list.remove(deletedIndex);

                final PrRecordBuilder record = dbPrHelper.getAllPr().get(finalPosition).getPrRecordBuilderList().get(viewHolder.getAdapterPosition());

                AchievementBuilder newAchievement = new AchievementBuilder.Builder(achievementBuilder).setPrRecord(list).create();

                String newString = objectConverter.objectToString(newAchievement);

                setBackgroundVisibilityIfEmpty(list, relativeLayout);

                dbPrHelper.deleteRow(finalPosition);
                dbPrHelper.replaceRow(newString, finalPosition);
                adapter.removeItem(viewHolder.getAdapterPosition());

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.delete_item), Snackbar.LENGTH_LONG);
                snackbar.setAction(getString(R.string.undo), view -> {
                    String news = objectConverter.objectToString(achievementBuilder);

                    dbPrHelper.replaceRow(news, finalPosition);
                    adapter.restoreItem(record, deletedIndex);
                    relativeLayout.setVisibility(View.INVISIBLE);
                });
                snackbar.setActionTextColor(ContextCompat.getColor(ViewAchievementActivity.this, R.color.statusBarColor));
                snackbar.show();
            }
        }
    };

    public void viewGraph(View view){
        if(dbPrHelper.getAllPr().get(finalPosition).getPrRecordBuilderList().size() > 1) {
            Intent intent = new Intent(ViewAchievementActivity.this, GraphActivity.class);
            String positionFroGraphActivity = Integer.toString(finalPosition);
            intent.putExtra(POSITION, positionFroGraphActivity);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You must have more than one PR", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewAchievementActivity.this, AchievementListActivity.class);
        startActivity(intent);
        return;
    }
}

class PrRecordValueComparator implements Comparator<PrRecordBuilder> {
    public int compare(PrRecordBuilder lhs, PrRecordBuilder rhs) {
        return rhs.getResult().compareTo(lhs.getResult());
    }
}