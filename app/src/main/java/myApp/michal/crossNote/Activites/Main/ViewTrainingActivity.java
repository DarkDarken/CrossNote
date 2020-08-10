package myApp.michal.crossNote.Activites.Main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Adapters.TemplateAdapter;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.ShareWorkoutHandler;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Code.Enums.EMotionNames;
import myApp.michal.crossNote.Code.Enums.EWorkoutNames;
import myApp.michal.crossNote.Code.Helper;
import myApp.michal.crossNote.Code.Interfaces.IBaseActivity;
import myApp.michal.crossNote.Converter.ObjectConverter;
import myApp.michal.crossNote.Databases.DbHelper;
import myApp.michal.crossNote.RecycleAdapters.RecyclerViewListAdapter;
import myApp.michal.crossNote.RecyclerItemTouchHelpers.RecyclerItemMainTouchHelper;
import myApp.michal.crossNote.RecyclerItemTouchHelpers.RecyclerItemViewTouchHelper;

public class ViewTrainingActivity extends AppCompatActivity implements RecyclerViewListAdapter.TrainingAdapterListener,
                                                                       IBaseActivity {
    private DbHelper m_dbHelper;
    private TextView m_textViewCategory, m_textViewTime, m_textViewResult;
    private Toolbar m_toolbar;
    private Window m_window;
    private RecyclerView m_recyclerView;
    private RecyclerViewListAdapter m_adapter;
    private CoordinatorLayout m_coordinatorLayout;
    private int m_finalPosition;
    private AlertDialog m_alertDialogSave, m_alertDialogAdd, m_alertDialogResult, m_alertDialogCategory;
    private View m_alertViewSave, m_alertViewAdd, m_alertViewResult, m_alertViewCategory;
    private TemplateAdapter<EMotionNames> m_categoryMotionAdapter;
    private TemplateAdapter<EWorkoutNames> m_categoryTrainingAdapter;
    private EditText m_editTextRepInChange, m_editTextRepInAdd, m_editTextResult, m_editTextLoadInChange, m_editTextLoadInAdd;
    private ToggleButton m_toggleButtonInChange, m_toggleButtonInAdd;
    private ObjectConverter m_objectConverter = new ObjectConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_training);

        m_finalPosition = Integer.parseInt(getIntent().getStringExtra(MainActivity.POSITION));

        initGUI();
        Setup();

        m_editTextRepInAdd = m_alertViewAdd.findViewById(R.id.repEdit);
        m_editTextLoadInAdd = m_alertViewAdd.findViewById(R.id.weightEdit);
        m_editTextRepInChange = m_alertViewSave.findViewById(R.id.repEdit);
        m_editTextLoadInChange = m_alertViewSave.findViewById(R.id.weightEdit);
        m_editTextResult = m_alertViewResult.findViewById(R.id.resultEdit);
        m_toggleButtonInChange = m_alertViewSave.findViewById(R.id.toggleButtonDialog);
        m_toggleButtonInAdd = m_alertViewAdd.findViewById(R.id.toggleButtonDialog);

        m_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        m_recyclerView.setItemAnimator(new DefaultItemAnimator());
        m_recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        m_recyclerView.setAdapter(m_adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemViewTouchHelper(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, listenerOnSwipe, listenerOnMove);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(m_recyclerView);
    }

    @SuppressLint("InflateParams")
    @Override
    public void initGUI(){
        m_dbHelper = new DbHelper(this);
        m_toolbar = findViewById(R.id.toolbar);
        m_window = this.getWindow();
        m_textViewCategory = findViewById(R.id.textCategory);
        m_textViewTime = findViewById(R.id.textTime);
        m_textViewResult = findViewById(R.id.textResultView);
        m_recyclerView = findViewById(R.id.RCView);
        m_coordinatorLayout = findViewById(R.id.coordinatorLayout);
        m_alertDialogSave = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();
        m_alertDialogAdd = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();
        m_alertDialogResult = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();
        m_alertDialogCategory = new AlertDialog.Builder(this, R.style.MyDialogTheme).create();
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        m_alertViewSave = inflater.inflate(R.layout.custom_alert_layout, null);
        m_alertViewAdd = inflater.inflate(R.layout.custom_alert_layout, null);
        m_alertViewResult = inflater.inflate(R.layout.dialog_result, null);
        m_alertViewCategory = inflater.inflate(R.layout.custom_alert_layout_only_spinner, null);
        m_categoryMotionAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_edit_item, R.id.spItem2, R.layout.spinner_item_drop_dialog, R.id.spDropItem2, EMotionNames.class);
        m_categoryTrainingAdapter = new TemplateAdapter<>(
                this, R.layout.spinner_edit_item, R.id.spItem2, R.layout.spinner_item_drop_dialog, R.id.spDropItem2, EWorkoutNames.class);
        m_adapter = new RecyclerViewListAdapter(this, m_dbHelper.getAll().get(m_finalPosition).getMotionList(), this);
    }

    private void Setup(){
        m_toolbar.setTitle(getString(R.string.title_activity_view_training));
        m_window.setStatusBarColor(getColor(R.color.statusBarColor));
        m_toolbar.setBackgroundColor(getColor(R.color.toolbarColor));
        m_textViewCategory.setText(m_dbHelper.getAll().get(m_finalPosition).getEWorkoutNames().name());
        m_textViewTime.setText(fillerTime(m_dbHelper.getAll().get(m_finalPosition).getTime()));
        setSupportActionBar(m_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String l_result = m_dbHelper.getAll().get(m_finalPosition).getResult();
        String l_tempResult = Helper.getResultString(m_dbHelper.getAll().get(m_finalPosition), l_result);
        m_textViewResult.setText(l_tempResult);
    }

    private void switchTextInout(){
        EWorkoutNames l_workoutNames = m_dbHelper.getAll().get(m_finalPosition).getEWorkoutNames();
        m_editTextResult.setInputType(l_workoutNames == EWorkoutNames.Benchmark ? InputType.TYPE_CLASS_TEXT
                                                                      : InputType.TYPE_CLASS_NUMBER);
    }

    private void switchDialogAppearance(int p_position){
        m_editTextRepInChange.setHintTextColor(getColor(R.color.gray));
        m_editTextLoadInChange.setHintTextColor(getColor(R.color.gray));
        m_editTextRepInAdd.setHintTextColor(getColor(R.color.gray));
        m_editTextLoadInAdd.setHintTextColor(getColor(R.color.gray));
        switch (p_position) {
            case 0: case 37: case 38: {
                setStatusOfVisibility(m_editTextRepInChange, m_editTextLoadInChange, View.INVISIBLE, m_toggleButtonInChange, View.VISIBLE);
                setStatusOfVisibility(m_editTextRepInAdd, m_editTextLoadInAdd, View.INVISIBLE, m_toggleButtonInAdd, View.VISIBLE);
                setWeightNull();
                break;
            }
            case 2: case 4: case 8: case 9: case 10: case 11: case 12: case 14: case 15: case 16:
            case 19: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 30:
            case 33: case 39: case 40: case 42: case 43: case 44: case 45: case 46: {
                setStatusOfVisibility(m_editTextRepInChange, m_editTextLoadInChange, View.VISIBLE, m_toggleButtonInChange, View.INVISIBLE);
                setStatusOfVisibility(m_editTextRepInAdd, m_editTextLoadInAdd, View.VISIBLE, m_toggleButtonInAdd, View.INVISIBLE);
                setWeightNull();
                break;
            }
            default: {
                setStatusOfVisibility(m_editTextRepInChange, m_editTextLoadInChange, View.INVISIBLE, m_toggleButtonInChange, View.INVISIBLE);
                setStatusOfVisibility(m_editTextRepInAdd, m_editTextLoadInAdd, View.INVISIBLE, m_toggleButtonInAdd, View.INVISIBLE);
            }
        }
    }

    private void setWeightNull(){
        m_editTextLoadInChange.setText("");
        m_editTextLoadInAdd.setText("");
    }

    private void setStatusOfVisibility(EditText p_editTextRep,
                                       EditText p_editTextLoad,
                                       int p_loadState,
                                       ToggleButton p_toggleButton,
                                       int p_toggleState){
        p_editTextRep.setVisibility(View.VISIBLE);
        p_editTextLoad.setVisibility(p_loadState);
        p_toggleButton.setVisibility(p_toggleState);
    }

    private String fillerTime(String p_time){
        String l_tempTime = p_time + "";
        String ONE_ELEM = "1";
        switch (m_dbHelper.getAll().get(m_finalPosition).getEWorkoutNames().name()){
            case "RFT":
                l_tempTime += (p_time.contentEquals(ONE_ELEM)) ? new StringBuilder().append(" ").append(getString(R.string.ROUND))
                                                               : new StringBuilder().append(" ").append(getString(R.string.ROUNDS));
                break;
            case "Benchmark":
                return l_tempTime;
            default:
                l_tempTime += (l_tempTime.contentEquals(ONE_ELEM) && l_tempTime.contains(".")) ? new StringBuilder().append(" ").append(getString(R.string.MINUTE))
                                                                                               : new StringBuilder().append(" ").append(getString(R.string.MINUTES));
        }
        return l_tempTime;
    }

    private void setOnItemSelectedSpinnerListener(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switchDialogAppearance(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe = new RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe() {
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
            if (viewHolder instanceof RecyclerViewListAdapter.MyViewHolder) {
                final TrainingBuilder trainingBuilder = m_dbHelper.getAll().get(m_finalPosition);

                List<MotionBuilder> list = m_dbHelper.getAll().get(m_finalPosition).getMotionList();

                final int deletedIndex = viewHolder.getAdapterPosition();

                list.remove(deletedIndex);

                final MotionBuilder motionBuilder = m_dbHelper.getAll().get(m_finalPosition).getMotionList().get(viewHolder.getAdapterPosition());

                TrainingBuilder newTraining = new TrainingBuilder.Builder(trainingBuilder).setMotionList(list).create();

                String newString = m_objectConverter.objectToString(newTraining);

                m_dbHelper.deleteRow(m_finalPosition);
                m_dbHelper.replaceRow(newString, m_finalPosition);
                m_adapter.removeItem(viewHolder.getAdapterPosition());

                Snackbar snackbar = Snackbar.make(m_coordinatorLayout, getString(R.string.delete_item), Snackbar.LENGTH_LONG);
                snackbar.setAction(getString(R.string.undo), v -> {
                    String news = m_objectConverter.objectToString(trainingBuilder);
                    m_dbHelper.replaceRow(news, m_finalPosition);
                    m_adapter.restoreItem(motionBuilder, deletedIndex);
                });
                snackbar.setActionTextColor(getColor(R.color.statusBarColor));
                snackbar.show();
            }

        }
    };

    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnMove listenerOnMove = new RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnMove() {
        @Override
        public boolean onMove(int firstPosition, int secondPosition) {
           m_adapter.onItemMove(firstPosition, secondPosition);
            final TrainingBuilder oldTraining = m_dbHelper.getAll().get(m_finalPosition);
            List<MotionBuilder> listFromAdapter = m_adapter.getBuilderList();
            List<MotionBuilder> listFromDb = oldTraining.getMotionList();
            List<MotionBuilder> listAfterMove = new ArrayList<>(listFromAdapter);
            listFromDb.clear();
            TrainingBuilder newTraining = new TrainingBuilder.Builder(oldTraining).setMotionList(listAfterMove).create();
            String newString = m_objectConverter.objectToString(newTraining);
            m_dbHelper.deleteRow(m_finalPosition);
            m_dbHelper.replaceRow(newString, m_finalPosition);
            return false;
        }
    };

    @Override
    public void onContactSelected(final int position) {
        m_alertDialogSave.setTitle("Edit the element");
        m_alertDialogSave.setView(m_alertViewSave);
        final Spinner mSpinner = m_alertViewSave.findViewById(R.id.categoryMotion);

        mSpinner.setPadding(13, 0, 0, 0);
        mSpinner.setAdapter(m_categoryMotionAdapter);
        m_dbHelper.getWritableDatabase();

        mSpinner.setSelection(m_dbHelper.getAll().get(m_finalPosition).getMotionList().get(position).getEMotionNames().ordinal());
        m_editTextRepInChange.setText(m_dbHelper.getAll().get(m_finalPosition).getMotionList().get(position).getRepetition());
        m_editTextLoadInChange.setText(m_dbHelper.getAll().get(m_finalPosition).getMotionList().get(position).getWeight());
        String status = m_dbHelper.getAll().get(m_finalPosition).getMotionList().get(position).isStatus() ? "Cals" : "Meters";
        m_toggleButtonInChange.setText(status);

        setOnItemSelectedSpinnerListener(mSpinner);
        m_alertDialogSave.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.SAVE), (dialog, id) -> {
            TrainingBuilder oldTraining = m_dbHelper.getAll().get(m_finalPosition);
            List<MotionBuilder> list = oldTraining.getMotionList();

            list.remove(position);

            List<MotionBuilder> tempList = new ArrayList<>(list);

            final String rep = m_editTextRepInChange.getText().toString();
            final EMotionNames category = (EMotionNames) mSpinner.getSelectedItem();
            final String mass = m_editTextLoadInChange.getText().toString();
            final boolean status1 = m_toggleButtonInChange.isChecked();

            MotionBuilder motion = new MotionBuilder.Builder().setEMotionNames(category)
                    .setRepetition(rep)
                    .setStatus(status1)
                    .setWeight(mass).create();

            tempList.add(position, motion);
            m_dbHelper.deleteRow(m_finalPosition);
            m_adapter.removeItem(position);
            m_adapter.restoreItem(motion, position);

            TrainingBuilder newTraining = new TrainingBuilder.Builder(oldTraining).setMotionList(tempList).create();
            String string = m_objectConverter.objectToString(newTraining);
            m_dbHelper.replaceRow(string, m_finalPosition);
            m_adapter.notifyDataSetChanged();
        });
        m_alertDialogSave.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.CANCEL), (dialog, id) -> {});
        m_alertDialogSave.show();

    }

    public void shareTraining(View view){
        ShareWorkoutHandler shareWorkoutHandler = new ShareWorkoutHandler(this, m_finalPosition, new DbHelper(this));
        shareWorkoutHandler.permissionCheck(PackageManager.PERMISSION_GRANTED);
    }

    public void changeResult(View view){
        m_alertDialogResult.setTitle("Edit the result");
        m_alertDialogResult.setView(m_alertViewResult);

        m_editTextResult.setHintTextColor(getColor(R.color.gray));
        m_editTextResult.setText(m_dbHelper.getAll().get(m_finalPosition).getResult());
        if(m_dbHelper.getAll().get(m_finalPosition).getEWorkoutNames() == EWorkoutNames.RFT){
            m_editTextResult.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        m_alertDialogResult.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.SAVE), (dialog, id) -> {
            TrainingBuilder oldTraining = m_dbHelper.getAll().get(m_finalPosition);

            final String result = m_editTextResult.getText().toString();

            m_dbHelper.deleteRow(m_finalPosition);

            TrainingBuilder newTraining = new TrainingBuilder.Builder(oldTraining).setResult(result).create();
            String string = m_objectConverter.objectToString(newTraining);
            m_dbHelper.replaceRow(string, m_finalPosition);
            String l_result = Helper.getResultString(oldTraining, result);
            m_textViewResult.setText(l_result);
            m_editTextResult.setInputType(InputType.TYPE_CLASS_NUMBER);
        });
        m_alertDialogResult.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.CANCEL), (dialog, id) -> {});
        m_alertDialogResult.show();

    }

    public void changeTime(View view){
        m_alertDialogResult.setTitle("Edit the time");
        m_alertDialogResult.setView(m_alertViewResult);

        m_editTextResult.setHintTextColor(getColor(R.color.gray));
        m_editTextResult.setText(m_dbHelper.getAll().get(m_finalPosition).getTime());
        switchTextInout();

        m_alertDialogResult.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.SAVE), (dialog, id) -> {
            TrainingBuilder oldTraining = m_dbHelper.getAll().get(m_finalPosition);
            String result = oldTraining.getResult();

            final String time = m_editTextResult.getText().toString();

            List<MotionBuilder> list = Helper.getBenchmarkFromTime(time);

            m_dbHelper.deleteRow(m_finalPosition);

            TrainingBuilder newTraining = list.isEmpty() ? new TrainingBuilder.Builder(oldTraining).setTime(time).create()
                                                         : new TrainingBuilder.Builder(oldTraining).setTime(time).setMotionList(list).create();

            String string = m_objectConverter.objectToString(newTraining);
            m_dbHelper.replaceRow(string, m_finalPosition);
            m_textViewTime.setText(new StringBuilder().append(fillerTime(time)));
            m_textViewResult.setText(Helper.getResultString(newTraining, result));
            m_adapter.restoreList(list, !list.isEmpty());
        });
        m_alertDialogResult.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.CANCEL), (dialog, id) -> {});
        m_alertDialogResult.show();
    }

    public void changeCategory(View view){
        m_alertDialogCategory.setTitle("Edit the category");
        m_alertDialogCategory.setView(m_alertViewCategory);

        final Spinner mSpinner = m_alertViewCategory.findViewById(R.id.categoryMotion);
        mSpinner.setAdapter(m_categoryTrainingAdapter);

        mSpinner.setSelection(m_dbHelper.getAll().get(m_finalPosition).getEWorkoutNames().ordinal());

        m_alertDialogCategory.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.SAVE), (dialog, id) -> {
            TrainingBuilder oldTraining = m_dbHelper.getAll().get(m_finalPosition);

            EWorkoutNames category = (EWorkoutNames) mSpinner.getSelectedItem();


            m_dbHelper.deleteRow(m_finalPosition);

            TrainingBuilder newTraining = new TrainingBuilder.Builder(oldTraining).setEWorkoutNames(category).create();
            String string = m_objectConverter.objectToString(newTraining);
            m_dbHelper.replaceRow(string, m_finalPosition);
            m_textViewCategory.setText(category.name());
            String l_result = oldTraining.getResult();

            m_textViewResult.setText(Helper.getResultString(newTraining, l_result));
            String l_time = oldTraining.getTime();
            m_textViewTime.setText(new StringBuilder().append(l_time).append(category.getNameTime(l_time)));
        });
        m_alertDialogCategory.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.CANCEL), (dialog, id) -> {});
        m_alertDialogCategory.show();
    }

    public void addMotion(View view){
        final Spinner pmSpinner = m_alertViewAdd.findViewById(R.id.categoryMotion);
        pmSpinner.setAdapter(m_categoryMotionAdapter);
        m_alertDialogAdd.setTitle("Add new motion");
        m_alertDialogAdd.setView(m_alertViewAdd);

        setOnItemSelectedSpinnerListener(pmSpinner);

        m_alertDialogAdd.setButton(DialogInterface.BUTTON_POSITIVE, "Add", (dialog, id) -> {
            TrainingBuilder p = m_dbHelper.getAll().get(m_finalPosition);

            List<MotionBuilder> motionBuilders = p.getMotionList();
            MotionBuilder motionBuilder = new MotionBuilder.Builder()
                    .setWeight(m_editTextLoadInAdd.getText().toString())
                    .setRepetition(m_editTextRepInAdd.getText().toString())
                    .setStatus(m_toggleButtonInAdd.isChecked())
                    .setEMotionNames((EMotionNames) pmSpinner.getSelectedItem()).create();
            motionBuilders.add(motionBuilder);

            TrainingBuilder trainingBuilder = new TrainingBuilder.Builder(p).setMotionList(motionBuilders).create();
            String stringNewP = m_objectConverter.objectToString(trainingBuilder);

            m_dbHelper.deleteRow(m_finalPosition);
            m_dbHelper.replaceRow(stringNewP, m_finalPosition);
            m_adapter.restoreItem(motionBuilder, motionBuilders.size() - 1);
            m_adapter.notifyDataSetChanged();

            m_editTextRepInAdd.setText("");
            m_editTextLoadInAdd.setText("");
            pmSpinner.setSelection(0);

            m_dbHelper.getWritableDatabase();
        });
        m_alertDialogAdd.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.CANCEL), (dialog, id) -> {});
        m_alertDialogAdd.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewTrainingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }
}
