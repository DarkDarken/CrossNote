package myApp.michal.crossNote.Code;

import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomCoparators implements Comparator<PrRecordBuilder> {
    private Logger logger = Logger.getAnonymousLogger();
    public int compare(PrRecordBuilder lhs, PrRecordBuilder rhs) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        int compareResult;
            try {
                Date arg0Date = format.parse(lhs.getDate());
                Date arg1Date = format.parse(rhs.getDate());
                compareResult = arg0Date.compareTo(arg1Date);
            } catch (ParseException e) {
                logger.log(Level.SEVERE, "PerseExeption", e);
                compareResult = lhs.getDate().compareTo(rhs.getDate());
            }
        return compareResult;
    }
}
