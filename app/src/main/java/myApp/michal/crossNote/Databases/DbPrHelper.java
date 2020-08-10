package myApp.michal.crossNote.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DbPrHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PrRecord.db";
    private static final int DATABASE_VERSION = 1;
    private static final String PR_TABLE_NAME = "achievements";
    private static final String PR_COLUMN_ID = "_id";
    private static final String PR_COLUMN_NAME = "product";

    private ObjectConverter objectConverter = new ObjectConverter();

    public DbPrHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PR_TABLE_NAME + "(" +
                PR_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PR_COLUMN_NAME + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PR_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAll(List<AchievementBuilder> list){
        ObjectConverter converter = new ObjectConverter();
        for(AchievementBuilder achievementBuilder : list){
            String name = converter.objectToString(achievementBuilder);
            insert(name);
        }
        return true;
    }

    public boolean insert(String training) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PR_COLUMN_NAME, training);
        db.insert(PR_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean replaceRow(String training, int position) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PR_COLUMN_ID, position + 1);
        contentValues.put(PR_COLUMN_NAME, training);
        db.replace(PR_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public void deleteRow(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PR_TABLE_NAME, null);
        cursor.moveToPosition(position);
        position = cursor.getInt(cursor.getColumnIndex(PR_COLUMN_ID));
        db.delete(PR_TABLE_NAME, PR_COLUMN_ID + "=?", new String[]{String.valueOf(position)});
    }

    public List<AchievementBuilder> getAllPr() {
        List<AchievementBuilder> achievementBuilders = new ArrayList<>();

        SQLiteDatabase dbHelper = this.getWritableDatabase();
        Cursor cursor = dbHelper.rawQuery("SELECT * FROM " + PR_TABLE_NAME, null);

        if (cursor.moveToNext()) {
            do {
                String temp = cursor.getString(cursor.getColumnIndex(PR_COLUMN_NAME));
                AchievementBuilder achievementBuilder = (AchievementBuilder) objectConverter.stringToObject(temp);
                achievementBuilders.add(achievementBuilder);
            } while (cursor.moveToNext());
        }
        dbHelper.close();

        return achievementBuilders;
    }

    public int dbSize(){
        return getAllPr().size();
    }

    public void sortAchievement() {
        List<AchievementBuilder> mainList = getAllPr();
        List<AchievementBuilder> tempList = new ArrayList<>();
        tempList.addAll(mainList);
        Collections.sort(tempList, new NameComparator());
        clearDatabase();
        for (AchievementBuilder achievementBuilder : tempList) {
            insert(objectConverter.objectToString(achievementBuilder));
        }
    }

    /*public void sortRecords(int position) {
        List<PrRecord> sortedList = new ArrayList<>();
        List<PrRecord> tempList = new ArrayList<>();
        AchievementBuilder achievementBuilder = getAllPr().get(position);
        sortedList.addAll(getAllPr().get(position).getPrRecordList());
        deleteRow(position);
        Collections.sort(sortedList, new NameComparator());
        for (int i = 0; i < sortedList.size(); i++) {
            tempList.add(sortedList.get(i));
        }
        MotionCategory category = achievementBuilder.getMotionCategory();
        insert(ObjectConverter.objectToString(new AchievementBuilder.Builder().setMotionCategory(category).setPrRecord(tempList).create()));
    }*/

    public void clearDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(PR_TABLE_NAME, null, null);
        db.close();
    }
}

    class NameComparator implements Comparator<AchievementBuilder> {
        public int compare(AchievementBuilder lhs, AchievementBuilder rhs) {
            return lhs.getMotionCategory().name().compareTo(rhs.getMotionCategory().name());
        }
    }


