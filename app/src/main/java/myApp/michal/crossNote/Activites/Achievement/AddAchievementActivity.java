package myApp.michal.crossNote.Activites.Achievement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Adapters.TemplateAdapter;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.Enums.EAchievementNames;
import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Code.Enums.EHeroNames;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbPrHelper;
import myApp.michal.crossNote.Fragments.DatePickerFragment;

public class AddAchievementActivity extends AppCompatActivity {

    private EditText dataEditText, resultEditText;
    private Spinner categorySpinner, benchmarkSpinner, heroSpinner;
    private DbPrHelper dbPrHelper;
    private ObjectConverter objectConverter = new ObjectConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_add_achievement));
        toolbar.setBackgroundColor(getColor(R.color.toolbarColor));
        setSupportActionBar(toolbar);

        Window window = this.getWindow();
        window.setStatusBarColor(getColor(R.color.statusBarColor));

        dataEditText = findViewById(R.id.dataData);
        resultEditText = findViewById(R.id.resultEditTextAddAchievement);

        dbPrHelper = new DbPrHelper(this);

        setCurrentDate(dataEditText);

        categorySpinner = findViewById(R.id.typeMotion);
        TemplateAdapter<EAchievementNames> categoryAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_item, R.id.spItem, R.layout.dropdown_item_spinner, R.id.spDropItem, EAchievementNames.class);
        categorySpinner.setAdapter(categoryAdapter);

        benchmarkSpinner = findViewById(R.id.typeBenchmark);
        TemplateAdapter<EBenchmarkNames> benchmarkCategoryAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_item, R.id.spItem, R.layout.dropdown_item_spinner, R.id.spDropItem, EBenchmarkNames.class);
        benchmarkSpinner.setAdapter(benchmarkCategoryAdapter);

        heroSpinner = findViewById(R.id.typeHero);
        TemplateAdapter<EHeroNames> heroCategoryAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_item, R.id.spItem, R.layout.dropdown_item_spinner, R.id.spDropItem, EHeroNames.class);
        heroSpinner.setAdapter(heroCategoryAdapter);

        categorySpinner.setVisibility(View.VISIBLE);
        benchmarkSpinner.setVisibility(View.INVISIBLE);
        heroSpinner.setVisibility(View.INVISIBLE);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 4){
                    categorySpinner.setVisibility(View.INVISIBLE);
                    categorySpinner.setEnabled(false);
                    heroSpinner.setVisibility(View.INVISIBLE);
                    heroSpinner.setEnabled(false);
                    benchmarkSpinner.setVisibility(View.VISIBLE);
                    benchmarkSpinner.setEnabled(true);
                } else if(position == 17) {
                    categorySpinner.setVisibility(View.INVISIBLE);
                    categorySpinner.setEnabled(false);
                    heroSpinner.setVisibility(View.VISIBLE);
                    heroSpinner.setEnabled(true);
                    benchmarkSpinner.setVisibility(View.INVISIBLE);
                    benchmarkSpinner.setEnabled(false);
                } else {
                    categorySpinner.setVisibility(View.VISIBLE);
                    categorySpinner.setEnabled(true);
                    heroSpinner.setVisibility(View.INVISIBLE);
                    heroSpinner.setEnabled(false);
                    benchmarkSpinner.setVisibility(View.INVISIBLE);
                    benchmarkSpinner.setEnabled(false);
                    }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        benchmarkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == EBenchmarkNames.values().length-1){
                    categorySpinner.setVisibility(View.VISIBLE);
                    categorySpinner.setEnabled(true);
                    benchmarkSpinner.setVisibility(View.INVISIBLE);
                    benchmarkSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        heroSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == EHeroNames.values().length-1){
                    categorySpinner.setVisibility(View.VISIBLE);
                    categorySpinner.setEnabled(true);
                    heroSpinner.setVisibility(View.INVISIBLE);
                    heroSpinner.setEnabled(false);
                    benchmarkSpinner.setVisibility(View.INVISIBLE);
                    benchmarkSpinner.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.add_achievement_button);
        fab.setOnClickListener(v -> {
            String date = dataEditText.getText().toString();
            String result = resultEditText.getText().toString();

            EAchievementNames achievementNames = (EAchievementNames) categorySpinner.getSelectedItem();
            EBenchmarkNames benchmarkNames = (EBenchmarkNames) benchmarkSpinner.getSelectedItem();
            EHeroNames heroNames = (EHeroNames) heroSpinner.getSelectedItem();

            boolean isAchievment = false;
            boolean isBenchmark = false;
            boolean isHero = false;
            for (AchievementBuilder motion : dbPrHelper.getAllPr()) {
                if (isPresentInListPr(motion, achievementNames, categorySpinner)) {
                    isAchievment = true;
                }
                if (isPresentInListPr(motion, benchmarkNames, benchmarkSpinner)) {
                    isBenchmark = true;
                }
                if (isPresentInListPr(motion, heroNames, heroSpinner)) {
                    isHero = true;
                }
            }
            if (!isAchievment && !isBenchmark && !isHero && resultEditText.getText().toString().length() != 0) {
                List<PrRecordBuilder> prRecordBuilderList = new ArrayList<>();
                prRecordBuilderList.add(new PrRecordBuilder.Builder().setDate(date).setResult(result).create());

                AchievementBuilder achievementBuilder;

                if (categorySpinner.getSelectedItem() == EAchievementNames.Benchmark) {
                    achievementBuilder = new AchievementBuilder.Builder().setPrRecord(prRecordBuilderList).setImage(R.mipmap.ic_record_girl_round).setMotionCategory(benchmarkNames).create();
                } else if (categorySpinner.getSelectedItem() == EAchievementNames.Hero) {
                    achievementBuilder = new AchievementBuilder.Builder().setPrRecord(prRecordBuilderList).setImage(R.mipmap.ic_record_hero_round).setMotionCategory(heroNames).create();
                } else {
                    achievementBuilder = new AchievementBuilder.Builder().setPrRecord(prRecordBuilderList).setImage(R.mipmap.ic_record_pr_round).setMotionCategory(achievementNames).create();
                }
                dbPrHelper.insert(objectConverter.objectToString(achievementBuilder));

                Intent intent = new Intent(this, AchievementListActivity.class);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, "You have this achievement or field is empty", Toast.LENGTH_SHORT);
                TextView textView = toast.getView().findViewById(android.R.id.message);
                if( v != null) textView.setGravity(Gravity.CENTER);
                toast.show();
            }


        });
    }

    private boolean isPresentInListPr(AchievementBuilder achievementBuilder, Enum<?> enums, Spinner spinner){
        return achievementBuilder.getMotionCategory().name().equals(enums.name()) && spinner.isEnabled();
    }

    private void setCurrentDate(EditText editText){
        final Calendar c = Calendar.getInstance(Locale.US);
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        String m = new DateFormatSymbols().getShortMonths()[mm];
        editText.setText(new StringBuilder().append(dd).append(" ").append(m).append(" ").append(yy));
    }

    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "dataPicker");

    }

}
