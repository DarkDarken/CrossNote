package myApp.michal.crossNote.Activites.Stoper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Achievement.AchievementListActivity;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Activites.Calculator.CalculatorActivity;
import myApp.michal.crossNote.Activites.EmomTimer.EmomTimerActivity;
import myApp.michal.crossNote.Activites.Info.InfoActivity;
import myApp.michal.crossNote.Activites.Main.MainActivity;
import myApp.michal.crossNote.Activites.Share.ShareActivity;
import myApp.michal.crossNote.Code.FiniteStateMachine.CStateStoper;
import myApp.michal.crossNote.Code.FiniteStateMachine.StateMachine;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;

public class StoperActivity extends BaseActivity {

    private StateMachine fsm;
    private DbHelper dbHelper;
    private DbPrHelper dbPrHelper;
    private DrawerLayout drawer;

    public static TextView s_minuteText, s_secondText, s_resetText, s_countDownTimerText;
    public static long s_millisecondTime, s_startTime, s_bufforTime, s_updateTime = 0L;
    public static int s_secondInInt, s_minuteInInt, s_milliSecondInInt;
    public static FloatingActionButton s_startButton;
    public static MediaPlayer s_shortBeep, s_longBeep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopers);
        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolbarAndWindow(toolbar, getString(R.string.title_activity_stopers));
        setActiveScreen();
        initGUI();
        setToggle(drawer, toolbar);

        s_minuteText.setOnLongClickListener(view -> { reset(view); return true;});
        s_countDownTimerText.setOnClickListener(this::start);

        setCounters(dbPrHelper, dbHelper);
    }

    @Override
    public void initGUI(){
        s_minuteText = findViewById(R.id.minute_text);
        s_secondText = findViewById(R.id.sec_text);
        s_startButton = findViewById(R.id.start_pause_stopper);
        s_resetText = findViewById(R.id.textView);
        s_countDownTimerText = findViewById(R.id.count_down_timer);
        drawer = findViewById(R.id.drawer_layout);
        s_minuteText.setVisibility(View.INVISIBLE);
        s_secondText.setVisibility(View.INVISIBLE);
        s_countDownTimerText.setText(R.string.Ten);
        fsm = new StateMachine(CStateStoper.STATE_IDLE);
        s_resetText.setVisibility(View.INVISIBLE);
        dbPrHelper = new DbPrHelper(this);
        dbHelper = new DbHelper(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        fsm.setState(CStateStoper.STATE_RESET_ALL);
        fsm.performEvent();
        startActivity(intent);
    }

    private void resetAll(){
        if(fsm.getState() != CStateStoper.STATE_COUNT_DOWN) {
            fsm.setState(CStateStoper.STATE_RESET_ALL);
            fsm.performEvent();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        Intent intent = new Intent();
        resetAll();
        switch (item.getItemId()){
            case R.id.nav_trainings: {
                intent = new Intent(StoperActivity.this, MainActivity.class);
                break;}
            case R.id.nav_stoper: {
                intent = new Intent(StoperActivity.this, StoperActivity.class);
                break;}
            case R.id.nav_stoper_emom: {
                intent = new Intent(StoperActivity.this, EmomTimerActivity.class);
                break;}
            case R.id.nav_achievement: {
                intent = new Intent(StoperActivity.this, AchievementListActivity.class);
                break;}
            case R.id.nav_calculator: {
                intent = new Intent(StoperActivity.this, CalculatorActivity.class);
                break;}
            case R.id.nav_info: {
                intent = new Intent(StoperActivity.this, InfoActivity.class);
                break;}
            case R.id.nav_share: {
                intent = new Intent(StoperActivity.this, ShareActivity.class);
                break;}
        }
        startActivity(intent);
        return true;
    }

    public void start(View view){
        s_shortBeep = MediaPlayer.create(this, R.raw.timer_beep);
        s_longBeep = MediaPlayer.create(this, R.raw.timer_beep_long);
        fsm.performEvent();
    }

    public void startTextView(View view) {
        start(view);
    }

    public void reset(View view){
        fsm.setState(CStateStoper.STATE_RESET);
        fsm.performEvent();
    }
}





