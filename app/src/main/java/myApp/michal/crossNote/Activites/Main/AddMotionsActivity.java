package myApp.michal.crossNote.Activites.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Adapters.TemplateAdapter;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;

public class AddMotionsActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private EditText repEditText, weightEditText;
    private TextInputLayout textInputRep, textInputWeight;
    private DbHelper dbHelper;
    private ToggleButton toggleButton;
    private ObjectConverter objectConverter = new ObjectConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_add_motions));
        toolbar.setBackgroundColor(getColor(R.color.toolbarColor));
        setSupportActionBar(toolbar);

        Window window = this.getWindow();
        window.setStatusBarColor(getColor(R.color.statusBarColor));

        InitUI();

        repEditText.setHintTextColor(getColor(R.color.gray));
        weightEditText.setHintTextColor(getColor(R.color.gray));
    }

    private void InitUI(){
        dbHelper = new DbHelper(this);
        repEditText = findViewById(R.id.repEdit);
        weightEditText = findViewById(R.id.weightEdit);
        textInputRep = findViewById(R.id.textInputRep);
        textInputWeight = findViewById(R.id.textInputWeight);
        categorySpinner = findViewById(R.id.motionSpinner);
        toggleButton = findViewById(R.id.toggleButton);
        TemplateAdapter<EMotionNames> categoryAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_item, R.id.spItem, R.layout.dropdown_item_spinner, R.id.spDropItem, EMotionNames.class);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int trainingCategory, long l) {
                switchCaseTextFiller(trainingCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void switchCaseTextFiller(int trainingCategory) {
        switch (trainingCategory) {
            case 2: case 4: case 8: case 9: case 10: case 11: case 12: case 14: case 15: case 16:
            case 19: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 30:
            case 33: case 39: case 40: case 42: case 43: case 44: case 46: {
                repEditText.setVisibility(View.VISIBLE);
                weightEditText.setVisibility(View.VISIBLE);
                toggleButton.setVisibility(View.INVISIBLE);
                textInputRep.setHint("Repetition");
                textInputWeight.setHint("Load");
                break;
            }
            case 0: case 37: case 38: {
                toggleState(this.toggleButton);
                weightEditText.setVisibility(View.INVISIBLE);
                toggleButton.setVisibility(View.VISIBLE);
                break;
            }
            default: {
                textInputRep.setHint("Repetition");
                weightEditText.setVisibility(View.INVISIBLE);
                toggleButton.setVisibility(View.INVISIBLE);
                textInputWeight.setHint("");
            }
        }
    }

    public void toggleState(View view) {
        textInputRep.setHint(((ToggleButton)view).isChecked() ? "Cals" : "Meters");
    }

    public void addMotion(){
        int size = dbHelper.getAll().size();
        TrainingBuilder p = dbHelper.getAll().get(size-1);

        List<MotionBuilder> motionBuilders = p.getMotionList();
        MotionBuilder motionBuilder = new MotionBuilder.Builder()
                .setStatus(toggleButton.isChecked())
                .setWeight(weightEditText.getText().toString())
                .setRepetition(repEditText.getText().toString())
                .setEMotionNames((EMotionNames) categorySpinner.getSelectedItem()).create();
        motionBuilders.add(motionBuilder);

        TrainingBuilder trainingBuilder = new TrainingBuilder.Builder(p)
                .setMotionList(motionBuilders).create();
        String stringNewP = objectConverter.objectToString(trainingBuilder);

        dbHelper.deleteRow(size-1);
        dbHelper.insert(stringNewP);
        resetEditTexts();
    }

    public void addMotionsListener(View view){
        addMotion();
        Dialog();
    }

    private void resetEditTexts(){
        weightEditText.setText("");
        repEditText.setText("");
    }

    private void Dialog(){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        TrainingBuilder trainingBuilder = dbHelper.getAll().get(dbHelper.dbSize()-1);
        String addedElementName = trainingBuilder.getMotionList().get(trainingBuilder.getMotionList().size()-1).getEMotionNames().name().replace("_", " ");
        alertDialog2.setTitle("Added: " + addedElementName + '\n' + "Did you finish adding element?");
        alertDialog2.setPositiveButton("YES", (dialog, id) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        alertDialog2.setNegativeButton("NO",
                (dialog, id) -> {});
        alertDialog2.show();
    }

}

