package myApp.michal.crossNote.Activites.Main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;
import myApp.michal.crossNote.RecycleAdapters.RecyclerMainListAdapter;
import myApp.michal.crossNote.RecyclerItemTouchHelpers.RecyclerItemMainTouchHelper;

public class MainActivity extends BaseActivity implements RecyclerMainListAdapter.TrainingAdapterListener {
    public static final String POSITION = "POSITION";

    private RecyclerView recyclerView;
    private RecyclerMainListAdapter mAdapter;
    private DbHelper dbHelper;
    private DbPrHelper dbPrHelper;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private RelativeLayout relativeLayout;
    private ObjectConverter objectConverter = new ObjectConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGUI();

        initToolbarAndWindow(toolbar, getString(R.string.title_activity_main));

        dbHelper.sortAndReverse();

        setDrawerLayoutBackground(dbHelper.getAll(), relativeLayout);

        mAdapter = new RecyclerMainListAdapter(this, dbHelper.getAll(), this);

        setRecyclerViewAdapter(this, mAdapter, recyclerView);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemMainTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, listenerOnSwipe);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        setToggle(drawerLayout, toolbar);
        setCounters(dbPrHelper, dbHelper);
    }

    @Override
    public void initGUI(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.RCViewMainList);
        drawerLayout = findViewById(R.id.drawer_layout);
        relativeLayout = findViewById(R.id.emptyLayout);
        dbHelper = new DbHelper(this);
        dbPrHelper = new DbPrHelper(this);
    }

    public void addWorkout(View view){
        Intent intent = new Intent(MainActivity.this, AddTrainingActivity.class);
        startActivity(intent);
    }

    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe =
            new RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe() {
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
            if (viewHolder instanceof RecyclerMainListAdapter.MyViewHolder) {
                final TrainingBuilder trainingBuilder = dbHelper.getAll().get(viewHolder.getAdapterPosition());

                final int deletedIndex = viewHolder.getAdapterPosition();

                dbHelper.deleteRow(viewHolder.getAdapterPosition());
                mAdapter.removeItem(viewHolder.getAdapterPosition());

                setBackgroundVisibilityIfEmpty(dbHelper.getAll(), relativeLayout);

                Snackbar.make(drawerLayout, getString(R.string.delete_item), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo), v -> {
                            String news = objectConverter.objectToString(trainingBuilder);

                            dbHelper.replaceRow(news, deletedIndex);
                            mAdapter.restoreItem(trainingBuilder, deletedIndex);
                            relativeLayout.setVisibility(View.INVISIBLE);
                        })
                        .setActionTextColor(ContextCompat.getColor(MainActivity.this, R.color.statusBarColor))
                        .show();
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search_action)
                .getActionView();
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        mSearchView.setQueryHint(getString(R.string.Search));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText.toString());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onContactSelected(int position) {
        if(mAdapter.getIds().isEmpty()){
            Intent intent = new Intent(MainActivity.this, ViewTrainingActivity.class);
            String POS2 = Integer.toString(position);
            intent.putExtra(POSITION, POS2);
            startActivity(intent);
        } else {
            int pos = mAdapter.getIds().get(position);
            Intent intent = new Intent(MainActivity.this, ViewTrainingActivity.class);
            String POS1 = Integer.toString(pos);
            intent.putExtra(POSITION, POS1);
            startActivity(intent);
        }
    }


}
