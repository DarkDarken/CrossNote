package myApp.michal.crossNote.Code.FiniteStateMachine;

public interface IHandlerEvent {
    void onPlayHandler();
    void onPauseHandler();
    void onResetHandler();
    void onFinishHandler();
    void onCountDownHandler();
    void onResetAll();
}
