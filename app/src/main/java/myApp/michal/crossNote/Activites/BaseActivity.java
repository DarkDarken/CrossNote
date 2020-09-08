package myApp.michal.crossNote.Activites;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Achievement.AchievementListActivity;
import myApp.michal.crossNote.Activites.Calculator.CalculatorActivity;
import myApp.michal.crossNote.Activites.EmomTimer.EmomTimerActivity;
import myApp.michal.crossNote.Activites.Info.InfoActivity;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Activites.Share.ShareActivity;
import myApp.michal.crossNote.Activites.Stoper.StoperActivity;
import myApp.michal.crossNote.Code.Interfaces.IBaseActivity;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        IBaseActivity {

    public void setToggle(DrawerLayout drawerLayout, Toolbar toolbar){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void initToolbarAndWindow(Toolbar toolbar, String title){

        toolbar.setTitle(title);
        Window window = this.getWindow();
        toolbar.setBackgroundColor(getColor(R.color.toolbarColor));
        window.setStatusBarColor(getColor(R.color.statusBarColor));
        setSupportActionBar(toolbar);
    }

    public void setDrawerLayoutBackground(List<?> list, RelativeLayout relativeLayout){
        relativeLayout.setVisibility(list.isEmpty() ? View.VISIBLE : View.INVISIBLE);
    }

    private String getCounter(int size){
        return size < 99 ? size + "" : "99+";
    }

    public void setCounters(DbPrHelper dbPrHelper, DbHelper dbHelper){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MenuItem menuItemForAchievement = navigationView.getMenu().findItem(R.id.nav_achievement);
        TextView counterText = (TextView) menuItemForAchievement.getActionView();
        counterText.setText(new StringBuilder().append(getCounter(dbPrHelper.dbSize())));

        MenuItem menuItemForWorkout = navigationView.getMenu().findItem(R.id.nav_trainings);
        TextView mainCounterText = (TextView) menuItemForWorkout.getActionView();
        mainCounterText.setText(new StringBuilder().append(getCounter(dbHelper.dbSize())));
    }

    public void setBackgroundVisibilityIfEmpty(List<?> list, RelativeLayout relativeLayout){
        if(list.isEmpty()){relativeLayout.setVisibility(View.VISIBLE);}
    }

    public void setActiveScreen(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void setRecyclerViewAdapter(Context context, RecyclerView.Adapter<?> adapter, RecyclerView recyclerView){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item){
       Intent intent = new Intent();
       switch (item.getItemId()){
           case R.id.nav_trainings: {
               intent = new Intent(this, MainActivity.class);
               break;}
           case R.id.nav_stoper: {
               intent = new Intent(this, StoperActivity.class);
               break;}
           case R.id.nav_stoper_emom: {
               intent = new Intent(this, EmomTimerActivity.class);
               break;}
           case R.id.nav_achievement: {
               intent = new Intent(this, AchievementListActivity.class);
               break;}
           case R.id.nav_calculator: {
               intent = new Intent(this, CalculatorActivity.class);
               break;}
           case R.id.nav_info: {
               intent = new Intent(this, InfoActivity.class);
               break;}
           case R.id.nav_share: {
               intent = new Intent(this, ShareActivity.class);
               break;}
       }
       startActivity(intent);
       return true;
   }

   @Override
   public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
