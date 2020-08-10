package myApp.michal.crossNote.Code.Classes;

import java.util.Comparator;

public class PrRecordValueComparator implements Comparator<PrRecordBuilder> {
    public int compare(PrRecordBuilder lhs, PrRecordBuilder rhs) {
        return rhs.getResult().compareTo(lhs.getResult());
    }
}
