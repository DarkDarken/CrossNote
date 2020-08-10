package myApp.michal.crossNote.Activites.EmomTimer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Code.FiniteStateMachine.CStateTimer;
import myApp.michal.crossNote.Code.FiniteStateMachine.StateMachine;
import myApp.michal.crossNote.Code.Globals;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;

public class EmomTimerActivity extends BaseActivity {

    private DbHelper dbHelper;
    private DbPrHelper dbPrHelper;
    private DrawerLayout drawer;
    private Globals globals = Globals.getInstance();
    private StateMachine fsm;

    public static TextView s_countDownText, s_resetText;
    public static long START_TIME_IN_MILLIS = 60000;
    public static long s_timeLeftInMillis = START_TIME_IN_MILLIS;
    public static CountDownTimer s_countDownTimer;
    public static boolean s_isRunning;
    public static long s_endTime;
    public static int s_position;
    public static TextView s_elementText, s_leftMinutes;
    public static Boolean s_isLoading;
    public static FloatingActionButton s_play;
    public static Context s_context;
    public static MediaPlayer s_shortBeep;
    public static MediaPlayer s_longBeep;
    public static Display s_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emom_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        initToolbarAndWindow(toolbar, getString(R.string.title_activity_test_emom_timer));

        setActiveScreen();

        initGUI();

        tryToGetPosition();

        fsm = new StateMachine(CStateTimer.STATE_TEST_IDLE);

        setToggle(drawer, toolbar);
        setCounters(dbPrHelper, dbHelper);
    }

    @Override
    public void initGUI(){
        dbHelper = new DbHelper(this);
        dbPrHelper = new DbPrHelper(this);
        drawer = findViewById(R.id.drawer_layout);
        s_countDownText = findViewById(R.id.counter);
        s_play = findViewById(R.id.start_emom_button);
        s_resetText = findViewById(R.id.resetText);
        s_elementText = findViewById(R.id.textTrening);
        s_leftMinutes = findViewById(R.id.leftMinute);
        s_resetText.setVisibility(View.INVISIBLE);
        s_context = getApplicationContext();
        s_isLoading = getIntent().getBooleanExtra(LoaderEmomListViewActivity.IS_LOADED, false);
        s_display = getWindowManager().getDefaultDisplay();
    }

    @Override
    public void onBackPressed() {
        reset();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        reset();
        super.onNavigationItemSelected(item);
        return true;
    }

    private void reset(){
        fsm.setState(CStateTimer.STATE_TEST_RESET);
        fsm.performEvent();
        s_countDownText.setEnabled(true);
        s_countDownText.setText("10");
    }

    public void load(View view){
        reset();
        Intent intent = new Intent(this, LoaderEmomListViewActivity.class);
        startActivity(intent);
    }

    public void startTextView(View view){
        start(view);
        s_countDownText.setEnabled(false);
    }

    public void start(View view){
        boolean l_isLoadingFlag = s_isLoading;
        boolean l_isEmptyFlag = false;
        if(dbHelper.getAll().size() != 0){
            l_isEmptyFlag= dbHelper.getAll().get(s_position).getMotionList().isEmpty();
        }
        if(l_isLoadingFlag && !l_isEmptyFlag){
            s_shortBeep = MediaPlayer.create(this, R.raw.timer_beep);
            s_longBeep = MediaPlayer.create(this, R.raw.timer_beep_long);
            fsm.performEvent();
        } else {
            Toast.makeText(this,
                    l_isEmptyFlag ? R.string.WorkoutIsEmpty
                                  : R.string.LoadTraining, Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View view){
        reset();
        fsm.setState(CStateTimer.STATE_TEST_CDT);
        s_countDownText.setEnabled(true);
    }

    private void tryToGetPosition(){
        if(s_isLoading){
            String positionString = getIntent().getStringExtra(LoaderEmomListViewActivity.POSITION_LOAD);
            globals.setTimerPosition(positionString);
            s_position = Integer.parseInt(positionString);
        }
    }
}
