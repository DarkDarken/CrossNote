package myApp.michal.crossNote.Code.FiniteStateMachine;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.Stoper.StoperActivity;

public class StoperHandlerEvent implements IHandlerEvent {

    private Handler m_handler;
    private Runnable m_runnable;
    private CountDownTimer m_countDownTimer;
    private static int COUNTER = 0;

    public StoperHandlerEvent(){
        m_handler = new Handler();
        m_runnable = new Runnable() {
            public void run() {

                StoperActivity.s_millisecondTime = SystemClock.uptimeMillis() - StoperActivity.s_startTime;

                StoperActivity.s_updateTime = StoperActivity.s_bufforTime + StoperActivity.s_millisecondTime;

                StoperActivity.s_secondInInt = (int) (StoperActivity.s_updateTime / 1000);

                StoperActivity.s_minuteInInt = StoperActivity.s_secondInInt / 60;

                StoperActivity.s_secondInInt = StoperActivity.s_secondInInt % 60;

                StoperActivity.s_milliSecondInInt = (int) (StoperActivity.s_updateTime % 1000);
                int l_newMiliSec = StoperActivity.s_milliSecondInInt /10;

                if(COUNTER > 3600){
                    StoperActivity.s_minuteText.setText(StoperActivity.s_minuteInInt + ":" + String.format("%02d", StoperActivity.s_secondInInt));
                } else {
                    StoperActivity.s_minuteText.setText(String.format("%01d", StoperActivity.s_secondInInt));
                }

                StoperActivity.s_secondText.setText(String.format("%02d", l_newMiliSec));
                m_handler.postDelayed(this, 0);
                COUNTER++;
            }
        };
    }

    @Override
    public void onPlayHandler() {
        StoperActivity.s_startButton.setImageResource(R.drawable.ic_pause_black_24dp);
        StoperActivity.s_startTime = SystemClock.uptimeMillis();
        m_handler.postDelayed(m_runnable, 0);
        StoperActivity.s_resetText.setEnabled(false);
        StoperActivity.s_resetText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPauseHandler() {
        StoperActivity.s_startButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        StoperActivity.s_bufforTime += StoperActivity.s_millisecondTime;
        m_handler.removeCallbacks(m_runnable);
        StoperActivity.s_resetText.setEnabled(true);
    }

    @Override
    public void onResetHandler() {
        StoperActivity.s_millisecondTime = 0L ;
        StoperActivity.s_startTime = 0L ;
        StoperActivity.s_bufforTime = 0L ;
        StoperActivity.s_updateTime = 0L ;
        StoperActivity.s_secondInInt = 0 ;
        StoperActivity.s_minuteInInt = 0 ;
        StoperActivity.s_milliSecondInInt = 0 ;
        StoperActivity.s_countDownTimerText.setVisibility(View.VISIBLE);
        StoperActivity.s_countDownTimerText.setText("10");
        StoperActivity.s_minuteText.setVisibility(View.INVISIBLE);
        StoperActivity.s_secondText.setVisibility(View.INVISIBLE);
        StoperActivity.s_resetText.setVisibility(View.INVISIBLE);
        StoperActivity.s_countDownTimerText.setEnabled(true);
        COUNTER = 0;
    }

    @Override
    public void onResetAll(){
        if(m_countDownTimer != null) {
            StoperActivity.s_startButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            onResetHandler();
            m_countDownTimer.cancel();
        }
    }

    @Override
    public void onCountDownHandler() {
        StoperActivity.s_startButton.setEnabled(false);
        StoperActivity.s_countDownTimerText.setEnabled(false);
        StoperActivity.s_startButton.setImageResource(R.drawable.ic_play_arrow_gray_24dp);
        m_countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                int l_sec = (int) (millisUntilFinished / 1000);
                StoperActivity.s_minuteText.setVisibility(View.INVISIBLE);
                StoperActivity.s_secondText.setVisibility(View.INVISIBLE);
                StoperActivity.s_countDownTimerText.setText(String.format("%02d", l_sec));
                if(l_sec < 4){
                    if(l_sec == 0){
                        StoperActivity.s_longBeep.start();
                    } else {
                        StoperActivity.s_shortBeep.start();
                    }
                }
            }
            public void onFinish() {
                onFinishHandler();
            }
        };
        m_countDownTimer.start();
    }

    @Override
    public void onFinishHandler(){
        StoperActivity.s_countDownTimerText.setVisibility(View.INVISIBLE);
        StoperActivity.s_minuteText.setVisibility(View.VISIBLE);
        StoperActivity.s_secondText.setVisibility(View.VISIBLE);
        StoperActivity.s_startButton.setEnabled(true);
        onPlayHandler();
    }
}
