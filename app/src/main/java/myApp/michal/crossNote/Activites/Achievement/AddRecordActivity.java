package myApp.michal.crossNote.Activites.Achievement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbPrHelper;
import myApp.michal.crossNote.Fragments.DatePickerFragment;

import static myApp.michal.crossNote.Activites.Achievement.ViewAchievementActivity.POSITION;

public class AddRecordActivity extends AppCompatActivity {

    private EditText dateText, resultText;
    private DbPrHelper dbPrHelper;
    private int finalPosition;
    private ObjectConverter objectConverter = new ObjectConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_add_record));
        toolbar.setBackgroundColor(getColor(R.color.toolbarColor));
        setSupportActionBar(toolbar);

        Window window = this.getWindow();
        window.setStatusBarColor(getColor(R.color.statusBarColor));

        dateText = findViewById(R.id.dataData);
        resultText = findViewById(R.id.resultEditTextAddRecord);
        dbPrHelper = new DbPrHelper(this);
        String positionString = getIntent().getStringExtra(POSITION);
        if(positionString != null) {
            finalPosition = Integer.parseInt(positionString);
        }
        setCurrentDate(dateText);
    }

    private void setCurrentDate(EditText editText){
        final Calendar calendar = Calendar.getInstance(Locale.US);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String nameOfMonth = new DateFormatSymbols().getShortMonths()[month];

        editText.setText(new StringBuilder().append(day).append(" ").append(nameOfMonth).append(" ").append(year));
    }

    public void addNewRecord(View view){
        AchievementBuilder oldAchievement = dbPrHelper.getAllPr().get(finalPosition);
        List<PrRecordBuilder> newListOfPr = oldAchievement.getPrRecordBuilderList();

        newListOfPr.add(new PrRecordBuilder.Builder().setResult(resultText.getText().toString())
                                                     .setDate(dateText.getText().toString()).create());

        AchievementBuilder newAchievement = new AchievementBuilder.Builder(oldAchievement).setPrRecord(newListOfPr).create();

        dbPrHelper.deleteRow(finalPosition);
        dbPrHelper.insert(objectConverter.objectToString(newAchievement));
        dbPrHelper.getWritableDatabase();

        startActivity(new Intent(this, AchievementListActivity.class));
    }

    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "dataPicker");

    }
}
