package myApp.michal.crossNote.Activites.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Adapters.TemplateAdapter;
import myApp.michal.crossNote.Code.Classes.DbBenchmark;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Code.Interfaces.IBaseActivity;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Fragments.DatePickerFragment;

public class AddTrainingActivity extends AppCompatActivity implements IBaseActivity {

    private EditText timeEditText, resultEditText, dataEditText;
    private Spinner categorySpinner, banchmarkSpinner;
    private TextInputLayout textInputTime, textInputResult;
    private DbHelper dbHelper;
    private LinearLayout linearLayout;
    private int benchmark;
    private CheckBox checkBox;
    private ObjectConverter objectConverter = new ObjectConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_add_training));
        toolbar.setBackgroundColor(getColor(R.color.toolbarColor));
        setSupportActionBar(toolbar);

        Window window = this.getWindow();
        window.setStatusBarColor(getColor(R.color.statusBarColor));

        initGUI();
        setOnClickAdaptersListener();
        setCurrentDate(dataEditText);
    }

    private void setCurrentDate(EditText editText){
        final Calendar c = Calendar.getInstance(Locale.US);
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        String m = new DateFormatSymbols().getShortMonths()[mm];

        editText.setText(new StringBuilder().append(dd).append(" ").append(m).append(" ").append(yy));
    }

    public void switchCaseTextFiller(int trainingCategory){
        switch(trainingCategory){
            case 1: {
                banchmarkSpinner.setVisibility(View.INVISIBLE);
                timeEditText.setVisibility(View.VISIBLE);
                textInputTime.setVisibility(View.VISIBLE);
                textInputTime.setHint("Time");
                textInputResult.setHint("");
                resultEditText.setVisibility(View.INVISIBLE);
                resultEditText.setText("");
                linearLayout.setVisibility(View.VISIBLE);
                break;
            }
            case 2: {
                banchmarkSpinner.setVisibility(View.INVISIBLE);
                timeEditText.setVisibility(View.VISIBLE);
                textInputTime.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                textInputTime.setHint("Rounds");
                textFiller();
                break;
            }
            case 3: {
                timeEditText.setVisibility(View.INVISIBLE);
                textInputTime.setVisibility(View.INVISIBLE);
                banchmarkSpinner.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                textFiller();
                break;
            }
            default: {
                banchmarkSpinner.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                timeEditText.setVisibility(View.VISIBLE);
                textInputTime.setVisibility(View.VISIBLE);
                textInputTime.setHint("Time");
                textFiller();
            }
        }
    }

    private void setOnClickAdaptersListener(){
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int trainingCategory, long l) {
                switchCaseTextFiller(trainingCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        banchmarkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int p_benchmark, long l) {
                if(p_benchmark == 4){
                    resultEditText.setVisibility(View.INVISIBLE);
                    textInputResult.setVisibility(View.INVISIBLE);
                    return;
                }
                resultEditText.setVisibility(View.VISIBLE);
                textInputResult.setVisibility(View.VISIBLE);
                benchmark = p_benchmark;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void textFiller(){
        textInputResult.setHint("Result");
        timeEditText.setHintTextColor(getColor(R.color.gray));
        resultEditText.setHintTextColor(getColor(R.color.gray));
        resultEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public void initGUI(){
        dbHelper = new DbHelper(this);
        linearLayout = findViewById(R.id.linearLayoutAddTraining);
        dataEditText = findViewById(R.id.dataData);
        timeEditText = findViewById(R.id.setTimeEdit);
        resultEditText = findViewById(R.id.resultEditTextAddTraining);
        textInputTime = findViewById(R.id.textInputTime);
        textInputResult = findViewById(R.id.textInputResultAddTraining);
        checkBox = findViewById(R.id.emonStateBox);
        categorySpinner = findViewById(R.id.typeWodSpinner);
        banchmarkSpinner = findViewById(R.id.banchmarkSpinner);
        banchmarkSpinner.setVisibility(View.INVISIBLE);
        TemplateAdapter<EBenchmarkNames> benchmarkAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_item, R.id.spItem, R.layout.dropdown_item_spinner, R.id.spDropItem, EBenchmarkNames.class);
        banchmarkSpinner.setAdapter(benchmarkAdapter);
        TemplateAdapter<EWorkoutNames> categoryAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_item, R.id.spItem, R.layout.dropdown_item_spinner, R.id.spDropItem, EWorkoutNames.class);
        categorySpinner.setAdapter(categoryAdapter);
    }

    public void addTrainingListener(View view){
        if(timeEditText.getText().toString().length() == 0 && categorySpinner.getSelectedItem() != EWorkoutNames.Benchmark){
            Toast.makeText(this, "First field shoud be greater than zero", Toast.LENGTH_SHORT).show();
        }else {
            addTraining();
        }
    }

    public void addTraining(){
        String date = dataEditText.getText().toString();
        String time = timeEditText.getText().toString();
        EWorkoutNames category = (EWorkoutNames) categorySpinner.getSelectedItem();
        List<MotionBuilder> motions = new ArrayList<>();
        String result = resultEditText.getText().toString();
        if(category == EWorkoutNames.Benchmark){
                motions = DbBenchmark.getBenchmark(EBenchmarkNames.values()[benchmark]);
                TrainingBuilder trainingBuilder = new TrainingBuilder.Builder().setData(date)
                        .setMotionList(motions)
                        .setTime(EBenchmarkNames.values()[benchmark].name())
                        .setEWorkoutNames(category)
                        .setResult(result).create();
                String training = objectConverter.objectToString(trainingBuilder);
                dbHelper.insert(training);
                Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
                startActivity(intent);
        } else {
            if(checkBox.isChecked()){
                motions.add(new MotionBuilder.Builder().setRepetition("Every minute").setEMotionNames(EMotionNames.EMPTY).create());
            }
            TrainingBuilder trainingBuilder = new TrainingBuilder.Builder().setData(date)
                    .setMotionList(motions)
                    .setTime(time)
                    .setEWorkoutNames(category)
                    .setResult(result)
                    .setEmomState(checkBox.isChecked()).create();
            String training = objectConverter.objectToString(trainingBuilder);
            dbHelper.insert(training);
            Intent intent = new Intent(AddTrainingActivity.this, AddMotionsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "dataPicker");

    }

}
