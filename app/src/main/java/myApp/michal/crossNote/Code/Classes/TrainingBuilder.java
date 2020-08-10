package myApp.michal.crossNote.Code.Classes;

import myApp.michal.crossNote.Code.Enums.EWorkoutNames;

import java.io.Serializable;
import java.util.List;

public class TrainingBuilder implements Serializable{

    private String date;
    private String time;
    private EWorkoutNames EWorkoutNames;
    private List<MotionBuilder> motionList;
    private String result;
    private boolean emomState;

    private TrainingBuilder(final Builder builder){
        date = builder.date;
        time = builder.time;
        EWorkoutNames = builder.EWorkoutNames;
        motionList = builder.motionList;
        result = builder.result;
        emomState = builder.emomState;
    }

    @Override
    public String toString() {
        return EWorkoutNames.name();
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public EWorkoutNames getEWorkoutNames() {
        return EWorkoutNames;
    }

    public List<MotionBuilder> getMotionList() {
        return motionList;
    }

    public String getResult() {return result; }

    public boolean getEmomState(){return emomState;}

    public static class Builder
    {
        private String date;
        private String time;
        private EWorkoutNames EWorkoutNames;
        private List<MotionBuilder> motionList;
        private String result;
        private boolean emomState = false;

        public Builder(TrainingBuilder trainingBuilder){
            this.date = trainingBuilder.date;
            this.time = trainingBuilder.time;
            this.EWorkoutNames = trainingBuilder.EWorkoutNames;
            this.motionList = trainingBuilder.motionList;
            this.result = trainingBuilder.result;
            this.emomState = trainingBuilder.emomState;
        }

        public Builder(){
        }

        public Builder setData(final String data){
            this.date = data;
            return this;
        }

        public Builder setTime(final String time){
            this.time = time;
            return this;
        }

        public Builder setEWorkoutNames(final EWorkoutNames EWorkoutNames) {
            this.EWorkoutNames = EWorkoutNames;
            return this;
        }

        public Builder setMotionList(final List<MotionBuilder> motionList) {
            this.motionList = motionList;
            return this;
        }

        public Builder setResult(final String result) {
            this.result = result;
            return this;
        }

        public Builder setEmomState(final boolean emomState){
            this.emomState = emomState;
            return this;
        }

        public TrainingBuilder create(){
            return new TrainingBuilder(this);
        }

    }
}

