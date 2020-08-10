package myApp.michal.crossNote.Code.Classes;

import myApp.michal.crossNote.Code.Enums.EMotionNames;

import java.io.Serializable;

public class MotionBuilder implements Serializable {

    static final long serialVersionUID = 40L;

    private String repetition;
    private EMotionNames EMotionNames;
    private String weight;
    private boolean status;

    private MotionBuilder(final Builder builder){
        repetition = builder.repetition;
        EMotionNames = builder.EMotionNames;
        weight = builder.weight;
        status = builder.status;
    }

    public String getRepetition() {
        return repetition;
    }

    public EMotionNames getEMotionNames() {
        return EMotionNames;
    }

    public String getWeight() {
        return weight;
    }

    public boolean isStatus() {
        return status;
    }

    public static class Builder
    {
        private String repetition;
        private EMotionNames EMotionNames;
        private String weight;
        private boolean status;

        public Builder setRepetition(final String repetition){
            this.repetition = repetition;
            return this;
        }

        public Builder setEMotionNames(final EMotionNames EMotionNames) {
            this.EMotionNames = EMotionNames;
            return this;
        }

        public Builder setWeight(final String weight){
            this.weight = weight;
            return this;
        }

        public Builder setStatus(final boolean status) {
            this.status = status;
            return this;
        }
        public MotionBuilder create(){
            return new MotionBuilder(this);
        }
    }
}
