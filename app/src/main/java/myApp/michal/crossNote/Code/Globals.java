package myApp.michal.crossNote.Code;

public class Globals {

    private String timerPosition = "0";
    private static final Globals ourInstance = new Globals();

    public static Globals getInstance() {
        return ourInstance;
    }

    private Globals() {
    }

    public String getTimerPosition(){
        return this.timerPosition;
    }

    public void setTimerPosition(String timerPosition){
        this.timerPosition = timerPosition;
    }
}
