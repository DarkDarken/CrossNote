package myApp.michal.crossNote;

import android.content.Context;

import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertEquals;



@RunWith(AndroidJUnit4.class)
public class DatabaseHelpersTestSuite implements GlobalConstant{
    private DbHelper dbHelper;
    private ObjectConverter objectConverter;
    private String wodAmrap;
    private String wodRft;
    private int EMPTY_LIST = 0;
    private int NUM_OF_SIZE_ONE = 1;
    private int FIRST_ELEMENT = 0;


    @Before
    public void SetUp(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        dbHelper = new DbHelper(appContext);
        objectConverter = new ObjectConverter();
        wodAmrap = objectConverter.objectToString(WOD_AMRAP);
        wodRft = objectConverter.objectToString(WOD_RFT);
    }

    private void insertFourTrainings(){
        for(int i = 0; i < 4; i++){
            dbHelper.insert(wodAmrap);
        }
    }

    @After
    public void TearDown(){
        dbHelper.clearDatabase();
    }

    @Test
    public void databaseIsEmptyOnInit() {
        assertEquals(EMPTY_LIST, dbHelper.dbSize());
    }

    @Test
    public void databaseIsNotEmptyAfterInsert() {
        dbHelper.insert(wodAmrap);
        assertEquals(NUM_OF_SIZE_ONE, dbHelper.dbSize());
    }

    @Test
    public void databaseIsEmptyAfterRemoveRow() {
        dbHelper.insert(wodAmrap);
        assertEquals(NUM_OF_SIZE_ONE, dbHelper.dbSize());
        dbHelper.deleteRow(FIRST_ELEMENT);
        assertEquals(EMPTY_LIST, dbHelper.dbSize());
    }
    @Test
    public void replaceSpecificRow() {
        insertFourTrainings();
        int NUM_OF_SIZE_FOUR = 4;
        assertEquals(NUM_OF_SIZE_FOUR, dbHelper.dbSize());
        int THIRD_ELEMENT = 2;
        dbHelper.replaceRow(wodRft, THIRD_ELEMENT);
        Utility.objectMatcher(dbHelper.getAll().get(THIRD_ELEMENT), (TrainingBuilder) objectConverter.stringToObject(wodRft));
    }

    @Test
    public void shouldReturnInsertObject(){
        dbHelper.insert(wodAmrap);
        assertEquals(AMRAP, dbHelper.getAll().get(FIRST_ELEMENT).getEWorkoutNames());
    }

    @Test
    public void shouldInsertListOfObject(){
        List<TrainingBuilder> list = Arrays.asList(WOD_AMRAP, WOD_RFT);
        assertEquals(EMPTY_LIST, dbHelper.dbSize());
        dbHelper.insertAll(list);
        int NUM_OF_SIZE_TWO = 2;
        assertEquals(NUM_OF_SIZE_TWO, dbHelper.dbSize());
    }

    @Test
    public void listIsNotSorted() {
        dbHelper.insert(wodAmrap);
        dbHelper.insert(wodRft);
        assertEquals(GLO_FALSE, dbHelper.isSorted());
    }

    @Test
    public void listIsSorted() {
        dbHelper.insert(wodRft);
        dbHelper.insert(wodAmrap);
        assertEquals(GLO_TRUE, dbHelper.isSorted());
    }

    @Test
    public void sortAndReverseListIsNeeded(){
        dbHelper.insert(wodAmrap);
        dbHelper.insert(wodRft);
        assertEquals(GLO_TRUE, dbHelper.sortAndReverse());
    }

    @Test
    public void sortAndReverseListIsNotNeeded(){
        dbHelper.insert(wodRft);
        dbHelper.insert(wodAmrap);
        assertEquals(GLO_FALSE, dbHelper.sortAndReverse());
    }

}
