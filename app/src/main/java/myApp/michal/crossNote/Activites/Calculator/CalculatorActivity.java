package myApp.michal.crossNote.Activites.Calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.Databases.DbPrHelper;

import static myApp.michal.crossNote.Code.Helper.getCalculatedRecord;

public class CalculatorActivity extends BaseActivity {

    private Spinner spinner;
    private EditText editPercent;
    private TextInputLayout inputPercent;
    private TextView viewWeight, emptyText;
    private DbPrHelper dbPrHelper;
    private DbHelper dbHelper;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        final Toolbar toolbar = findViewById(R.id.toolbar);

        initToolbarAndWindow(toolbar, getString(R.string.title_activity_calculator));

        initGUI();

        final FloatingActionButton fab = findViewById(R.id.calculate_button);

        List<String> test = dbPrHelper.getAllPr().stream().filter(x -> x.getPrRecordBuilderList().size() != 0)
                                                          .map(y -> y.getMotionCategory().name().replace("_", " "))
                                                          .collect(Collectors.toList());
        if(test.isEmpty()){
            emptyText.setVisibility(View.VISIBLE);
            inputPercent.setHint("");
            editPercent.setEnabled(false);

        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.item, test);
        arrayAdapter.setDropDownViewResource(R.layout.dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final List<AchievementBuilder> list = dbPrHelper.getAllPr();

                IntPredicate is = (int x) -> list.get(x).getMotionCategory().name().equals(spinner.getSelectedItem().toString().replace(" ", "_"));

                final int finalTemp = IntStream.range(0, list.size()).filter(is).findFirst().orElse(0);

                fab.setOnClickListener(v -> {
                    if (!editPercent.getText().toString().isEmpty()) {
                        viewWeight.setText(getCalculatedRecord(editPercent.getText().toString(), list.get(finalTemp)));
                    } else {
                        Toast.makeText(CalculatorActivity.this, "Empty percent", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        setToggle(drawer, toolbar);
        setCounters(dbPrHelper, dbHelper);
    }

    @Override
    public void initGUI(){
        spinner = findViewById(R.id.spinnerCalculator);
        editPercent = findViewById(R.id.editPercentCalculator);
        inputPercent = findViewById(R.id.inputPercentCalculator);
        viewWeight = findViewById(R.id.viewWeightCalculator);
        drawer = findViewById(R.id.drawer_layout);
        emptyText = findViewById(R.id.emptyText);
        emptyText.setVisibility(View.INVISIBLE);

        inputPercent.setHint("Percent");

        dbPrHelper = new DbPrHelper(this);
        dbHelper = new DbHelper(this);

        editPercent.setHintTextColor(getColor(R.color.gray));
    }
}
