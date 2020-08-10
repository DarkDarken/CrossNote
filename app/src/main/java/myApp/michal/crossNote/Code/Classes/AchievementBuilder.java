package myApp.michal.crossNote.Code.Classes;

import java.io.Serializable;
import java.util.List;

public class AchievementBuilder implements Serializable {

    private Enum<?> achievementCategory;
    private List<PrRecordBuilder> prRecordBuilderList;
    private int image;

    private AchievementBuilder(final Builder builder){
        prRecordBuilderList = builder.prRecordBuilderList;
        achievementCategory = builder.achievementCategory;
        image = builder.image;
    }

    public List<PrRecordBuilder> getPrRecordBuilderList() {
        return prRecordBuilderList;
    }

    public Enum<?> getMotionCategory() {
        return achievementCategory;
    }

    public int getImage(){return image;}

    public static class Builder {
        private List<PrRecordBuilder> prRecordBuilderList;
        private Enum<?> achievementCategory;
        private int image;

        public Builder(AchievementBuilder achievementBuilder){
            this.prRecordBuilderList = achievementBuilder.prRecordBuilderList;
            this.achievementCategory = achievementBuilder.achievementCategory;
            this.image = achievementBuilder.image;
        }

        public Builder(){
        }

        public Builder setPrRecord(final List<PrRecordBuilder> prRecordBuilderList) {
            this.prRecordBuilderList = prRecordBuilderList;
            return this;
        }

        public Builder setMotionCategory(final Enum<?> achievementCategory) {
            this.achievementCategory = achievementCategory;
            return this;
        }

        public Builder setImage(final int image){
            this.image = image;
            return this;
        }

        public AchievementBuilder create(){
            return new AchievementBuilder(this);
        }
    }
}
