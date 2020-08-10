package myApp.michal.crossNote.Activites.Achievement;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;
import myApp.michal.crossNote.RecycleAdapters.RecyclerPrMainListAdapter;
import myApp.michal.crossNote.RecyclerItemTouchHelpers.RecyclerItemMainTouchHelper;
import myApp.michal.crossNote.RecyclerItemTouchHelpers.RecyclerItemPrMainTouchHelper;

public class AchievementListActivity extends BaseActivity implements RecyclerPrMainListAdapter.PrAdapterListener {

    public static final String POSITION_X = "POSITION";

    private RecyclerView recyclerView;
    private RecyclerPrMainListAdapter adapter;
    private DrawerLayout drawer;
    private DbPrHelper dbPrHelper;
    private DbHelper dbHelper;
    private RelativeLayout relativeLayout;
    private ObjectConverter objectConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_list);
        Toolbar toolbar = findViewById(R.id.toolbar);

        initToolbarAndWindow(toolbar, getString(R.string.title_activity_achievement_list));

        initGUI();

        dbPrHelper.sortAchievement();

        setDrawerLayoutBackground(dbPrHelper.getAllPr(), relativeLayout);

        adapter = new RecyclerPrMainListAdapter(this, dbPrHelper.getAllPr(), this);

        setRecyclerViewAdapter(this, adapter, recyclerView);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemPrMainTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, listenerOnSwipe);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        setToggle(drawer, toolbar);

        setCounters(dbPrHelper, dbHelper);
    }

    @Override
    public void initGUI(){
        recyclerView = findViewById(R.id.RCViewAchievementList);
        dbPrHelper = new DbPrHelper(this);
        dbHelper = new DbHelper(this);
        objectConverter = new ObjectConverter();
        relativeLayout = findViewById(R.id.emptyLayout);
        drawer = findViewById(R.id.drawer_layout);
    }

    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe = new RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe() {
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
            if (viewHolder instanceof RecyclerPrMainListAdapter.MyViewHolder) {
                final AchievementBuilder achievementBuilder = dbPrHelper.getAllPr().get(viewHolder.getAdapterPosition());

                final int deletedIndex = viewHolder.getAdapterPosition();

                dbPrHelper.deleteRow(viewHolder.getAdapterPosition());
                adapter.removeItem(viewHolder.getAdapterPosition());

                setBackgroundVisibilityIfEmpty(dbPrHelper.getAllPr(), relativeLayout);


                Snackbar.make(drawer, getString(R.string.delete_item), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo), view -> {
                            String news = objectConverter.objectToString(achievementBuilder);

                            dbPrHelper.replaceRow(news, deletedIndex);
                            adapter.restoreItem(achievementBuilder, deletedIndex);

                            relativeLayout.setVisibility(View.INVISIBLE);
                        }).setActionTextColor(getColor(R.color.statusBarColor)).show();
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AchievementListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_action)
                .getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
        }
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("Search...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String querySubmit) {
               adapter.getFilter().filter(querySubmit);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryChange) {
               adapter.getFilter().filter(queryChange);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void addAchievement(View view){
        Intent intent = new Intent(AchievementListActivity.this, AddAchievementActivity.class);
        startActivity(intent);
    }

    @Override
    public void onContactSelected(int position) {
        Intent intent = new Intent(AchievementListActivity.this, ViewAchievementActivity.class);
        String newPosition;
        if(adapter.getIds().isEmpty()){
            newPosition = Integer.toString(position);
        } else {
            int pos = adapter.getIds().get(position);
            newPosition = Integer.toString(pos);
        }
        intent.putExtra(POSITION_X, newPosition);
        startActivity(intent);
    }
}
