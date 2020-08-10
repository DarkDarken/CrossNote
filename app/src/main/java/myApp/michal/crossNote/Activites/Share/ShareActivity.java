package myApp.michal.crossNote.Activites.Share;

import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Code.Classes.ShareWorkoutHandler;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;
import myApp.michal.crossNote.RecycleAdapters.RecyclerMainListAdapter;

public class ShareActivity extends BaseActivity implements RecyclerMainListAdapter.TrainingAdapterListener {

    private DbHelper dbHelper;
    private DbPrHelper dbPrHelper;
    private RecyclerView recyclerView;
    private RecyclerMainListAdapter mAdapter;
    private RelativeLayout relativeLayout;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Toolbar toolbar = findViewById(R.id.toolbar);

        initToolbarAndWindow(toolbar, getString(R.string.title_activity_share));

        initGUI();

        dbHelper.sortAndReverse();

        setDrawerLayoutBackground(dbHelper.getAll(), relativeLayout);

        setRecyclerViewAdapter(this, mAdapter, recyclerView);

        setToggle(drawer, toolbar);

        setCounters(dbPrHelper, dbHelper);
    }

    @Override
    public void initGUI(){
        dbHelper = new DbHelper(this);
        dbPrHelper = new DbPrHelper(this);
        recyclerView = findViewById(R.id.RCViewShare);
        relativeLayout = findViewById(R.id.emptyLayout);
        drawer = findViewById(R.id.drawer_layout);
        mAdapter = new RecyclerMainListAdapter(this, dbHelper.getAll(), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager l_searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView l_SearchView = (SearchView) menu.findItem(R.id.search_action)
                .getActionView();
        if (l_searchManager != null) {
            l_SearchView.setSearchableInfo(l_searchManager
                    .getSearchableInfo(getComponentName()));
        }
        l_SearchView.setMaxWidth(Integer.MAX_VALUE);

        l_SearchView.setQueryHint("Search...");

        l_SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        ShareWorkoutHandler shareWorkoutHandler;
        if(mAdapter.getIds().isEmpty()){
            shareWorkoutHandler = new ShareWorkoutHandler(this, position, dbHelper);
        } else {
            int pos = mAdapter.getIds().get(position);
            shareWorkoutHandler = new ShareWorkoutHandler(this, pos, dbHelper);
        }
        shareWorkoutHandler.permissionCheck(PackageManager.PERMISSION_GRANTED);
    }
}
