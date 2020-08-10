package myApp.michal.crossNote.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 1;
    private static final String PRODUCT_TABLE_NAME = "products";
    private static final String PRODUCT_COLUMN_ID = "_id";
    private static final String PRODUCT_COLUMN_NAME = "product";

    private ObjectConverter objectConverter = new ObjectConverter();

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PRODUCT_TABLE_NAME + "(" +
                PRODUCT_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PRODUCT_COLUMN_NAME + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        onCreate(db);
    }

    public void insertAll(List<TrainingBuilder> list){
        ObjectConverter converter = new ObjectConverter();
        for(TrainingBuilder trainingBuilder : list){
            String name = converter.objectToString(trainingBuilder);
            insert(name);
        }
    }

    public boolean insert(String training) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COLUMN_NAME, training);
        db.insert(PRODUCT_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public void replaceRow(String training, int position){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COLUMN_ID, position+1);
        contentValues.put(PRODUCT_COLUMN_NAME, training);
        db.replace(PRODUCT_TABLE_NAME, null, contentValues);
        db.close();
    }

    public void deleteRow(int position){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PRODUCT_TABLE_NAME, null );
        cursor.moveToPosition(position);
        position = cursor.getInt(cursor.getColumnIndex(DbHelper.PRODUCT_COLUMN_ID));
        db.delete(PRODUCT_TABLE_NAME,PRODUCT_COLUMN_ID+"=?",new String[]{String.valueOf(position)});
    }

    public boolean sortAndReverse(){
        List<TrainingBuilder> sortedList = new ArrayList<>(getAll());
        if(!isSorted()) {
            clearDatabase();
            Collections.sort(sortedList, new StringDateComparator());
            Collections.reverse(sortedList);
            for (int i = 0; i < sortedList.size(); i++) {
                insert(objectConverter.objectToString(sortedList.get(i)));
            }
            return true;
        }
        return false;
    }

    public int dbSize(){
        return getAll().size();
    }

    public boolean isSorted(){
        List<TrainingBuilder> sortedList = new ArrayList<>(getAll());
        if(!sortedList.isEmpty()) {
            TrainingBuilder prev = sortedList.get(0);
            for (TrainingBuilder item : sortedList) {
                if (new StringDateComparator().compare(item, prev) == 1) {
                    return false;
                }
                prev = item;
            }
        }
            return true;

    }

    public List<TrainingBuilder> getAll(){
        List<TrainingBuilder> trainingBuilders = new ArrayList<>();

        SQLiteDatabase dbHelper = this.getWritableDatabase();
        Cursor cursor = dbHelper.rawQuery("SELECT * FROM " + PRODUCT_TABLE_NAME, null );

        if(cursor.moveToNext()){
            do {
                String temp = cursor.getString(cursor.getColumnIndex(PRODUCT_COLUMN_NAME));
                TrainingBuilder trainingBuilder = (TrainingBuilder) objectConverter.stringToObject(temp);
                trainingBuilders.add(trainingBuilder);
            }while (cursor.moveToNext());
        }
        dbHelper.close();

        return trainingBuilders;
    }

    public void clearDatabase(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(PRODUCT_TABLE_NAME, null, null);
        db.close();
    }
}

class StringDateComparator implements Comparator<TrainingBuilder> {
    private Logger logger = Logger.getAnonymousLogger();
    public int compare(TrainingBuilder lhs, TrainingBuilder rhs) {
        SimpleDateFormat format = new SimpleDateFormat(
                "dd MMM yyyy");
        int compareResult;
        try {
            Date arg0Date = format.parse(lhs.getDate());
            Date arg1Date = format.parse(rhs.getDate());
            compareResult = arg0Date.compareTo(arg1Date);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            compareResult = rhs.getDate().compareTo(lhs.getDate());
        }
        return compareResult;
    }
}

