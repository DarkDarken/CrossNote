package myApp.michal.crossNote.Code.FiniteStateMachine;


import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import myApp.michal.crossNote.Activites.EmomTimer.EmomTimerActivity;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Databases.DbHelper;

import static myApp.michal.app_02.R.drawable;
import static myApp.michal.app_02.R.string;

public class TimerHandlerEvent implements IHandlerEvent {

    private static int m_iter = 0;
    private DbHelper dbHelper;
    private int time;
    private List<MotionBuilder> tempList;

    public TimerHandlerEvent(){
        dbHelper = new DbHelper(EmomTimerActivity.s_context);
        mainCountDownTimerRunner();
    }

    @Override
    public void onResetAll() {}

    @Override
    public void onPlayHandler() {
        EmomTimerActivity.s_play.setEnabled(true);
        EmomTimerActivity.s_endTime = System.currentTimeMillis() + EmomTimerActivity.s_timeLeftInMillis;
        if(!dbHelper.getAll().get(EmomTimerActivity.s_position).getEmomState()) {
            tempList = returnArray(dbHelper.getAll().get(EmomTimerActivity.s_position).getMotionList(), getTime());
            EmomTimerActivity.s_elementText.setText(stringBuilder(tempList, m_iter));
            EmomTimerActivity.s_elementText.setTextSize(resolveTextSize(stringBuilder(tempList, m_iter)));
            if (m_iter == 0) {
                EmomTimerActivity.s_leftMinutes.setText(/*tempList.size() - 1*/ "1"+ "/" + tempList.size());
            }
        } else {
            EmomTimerActivity.s_elementText.setText(getStringList(EmomTimerActivity.s_position));
            EmomTimerActivity.s_elementText.setTextSize(resolveTextSize(getStringList(EmomTimerActivity.s_position)));
            if (m_iter == 0) {
                EmomTimerActivity.s_leftMinutes.setText(/*getTime() - 1*/ "1"+ "/" + getTime());
            }
        }
        mainCountDownTimerRunner();

        EmomTimerActivity.s_countDownTimer.start();

        EmomTimerActivity.s_isRunning = true;
        updateButtons();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onPauseHandler() {
        EmomTimerActivity.s_countDownTimer.cancel();
        EmomTimerActivity.s_isRunning = false;
        EmomTimerActivity.s_play.setVisibility(View.INVISIBLE);
        EmomTimerActivity.s_resetText.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResetHandler() {
        EmomTimerActivity.s_timeLeftInMillis = EmomTimerActivity.START_TIME_IN_MILLIS;
        EmomTimerActivity.s_elementText.setText("");
        EmomTimerActivity.s_leftMinutes.setText("");
        m_iter = 0;
        updateCountDownText();
        EmomTimerActivity.s_play.setVisibility(View.VISIBLE);
        EmomTimerActivity.s_resetText.setVisibility(View.INVISIBLE);
        EmomTimerActivity.s_countDownTimer.cancel();
        updateButtons();
    }

    @Override
    public void onFinishHandler() {
        EmomTimerActivity.s_longBeep.start();
        EmomTimerActivity.s_countDownText.setText(string.Finish);
        EmomTimerActivity.s_elementText.setText("");
        EmomTimerActivity.s_leftMinutes.setText("");
        EmomTimerActivity.s_countDownTimer.cancel();
        EmomTimerActivity.s_isRunning = false;
    }

    @Override
    public void onCountDownHandler() {
        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                int l_sec = (int) (millisUntilFinished / 1000);
                EmomTimerActivity.s_countDownText.setText(String.format("%02d", l_sec));
                if(l_sec < 4){
                    if(l_sec == 0){
                        EmomTimerActivity.s_longBeep.start();
                    } else {
                        EmomTimerActivity.s_shortBeep.start();
                    }
                }
                EmomTimerActivity.s_play.setEnabled(false);
                EmomTimerActivity.s_play.setImageResource(drawable.ic_play_arrow_gray_24dp);
            }
            public void onFinish() {
                onPlayHandler();
            }
        };
        countDownTimer.start();
    }

    private static void updateCountDownText(){
        int l_seconds = (int) (EmomTimerActivity.s_timeLeftInMillis/1000)%60;

        String l_timeLeftFormatted = String.format(Locale.getDefault(), "%02d", l_seconds);

        EmomTimerActivity.s_countDownText.setText(l_timeLeftFormatted);
    }

    private void mainCountDownTimerRunner(){
        EmomTimerActivity.s_countDownTimer = new CountDownTimer(EmomTimerActivity.s_timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                EmomTimerActivity.s_timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                m_iter++;
                if(!dbHelper.getAll().get(EmomTimerActivity.s_position).getEmomState()){
                    if(m_iter > tempList.size()-1){
                        onFinishHandler();
                    } else {
                        EmomTimerActivity.s_timeLeftInMillis = EmomTimerActivity.START_TIME_IN_MILLIS;
                        EmomTimerActivity.s_elementText.setText(stringBuilder(tempList, m_iter));
                        EmomTimerActivity.s_leftMinutes.setText(m_iter + 1 + "/" + tempList.size());
                        EmomTimerActivity.s_isRunning = true;
                        EmomTimerActivity.s_countDownTimer.start();
                    }
                }else {
                    if(m_iter == getTime()){
                        onFinishHandler();
                    } else {
                        EmomTimerActivity.s_timeLeftInMillis = EmomTimerActivity.START_TIME_IN_MILLIS;
                        EmomTimerActivity.s_elementText.setText(getStringList(EmomTimerActivity.s_position));
                        EmomTimerActivity.s_leftMinutes.setText(m_iter + 1 + "/" + getTime());
                        EmomTimerActivity.s_countDownTimer.start();
                        EmomTimerActivity.s_isRunning = true;
                    }
                }
                updateButtons();
            }
        };
    }

    private static void updateButtons(){
        if(EmomTimerActivity.s_isRunning){
            EmomTimerActivity.s_resetText.setVisibility(View.INVISIBLE);
            EmomTimerActivity.s_play.setImageResource(drawable.ic_stop_black_24dp);
        } else {
            EmomTimerActivity.s_play.setImageResource(drawable.ic_play_arrow_black_24dp);
        }
    }

    private List<MotionBuilder> returnArray(List<MotionBuilder> array, int time){
        List<MotionBuilder> l_tempList = new ArrayList<>();
        for(int i=0; i<time/array.size(); i++){
            for(int j=0; j<array.size(); j++) {
                l_tempList.add(array.get(j));
            }
        }
        return l_tempList;
    }

    private int getTime(){
        if(dbHelper.getAll().size() != 0) {
            return time = Integer.parseInt(dbHelper.getAll().get(EmomTimerActivity.s_position).getTime());
        }
        return 0;
    }

    private String stringBuilder(List<MotionBuilder> list, int i){
        StringBuilder sb = new StringBuilder();
        String motion = list.get(i).getEMotionNames().name().replace("_", " ");
        return sb.append(motion).toString();
    }

    private String getStringList(int position){
        List<MotionBuilder> list = dbHelper.getAll().get(position).getMotionList();
        list.remove(0);
        String name = "";
        for(MotionBuilder motionBuilder : list){
            name += motionBuilder.getEMotionNames().name().replace("_", " ") + '\n';
        }
        return name;
    }

    private float resolveTextSize(String element){
        Point size = new Point();
        EmomTimerActivity.s_display.getSize(size);
        int windowSize = size.x;
        double elementSize = element.length();
        double percent = 0.7;
        double avSize = percent*windowSize;
        float res = (float) (avSize/elementSize);
        return res > 60 ? 60 : res;
    }
}
