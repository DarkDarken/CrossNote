package myApp.michal.crossNote.Code.Classes;

import java.io.Serializable;

public class PrRecordBuilder implements Serializable{
    private String result;
    private String date;

    private PrRecordBuilder(final Builder builder){
        result = builder.result;
        date = builder.date;
    }

    public String getResult() { return result; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class Builder {
        private String result;
        private String date;

        public Builder setResult(final String result) {
            this.result = result;
            return this;
        }

        public Builder setDate(final String date) {
            this.date = date;
            return this;
        }

        public PrRecordBuilder create(){
            return new PrRecordBuilder(this);
        }

    }
}
