package myApp.michal.crossNote.Activites.Achievement;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PanZoom;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Activites.BaseActivity;
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.CustomCoparators;
import myApp.michal.crossNote.Databases.DbPrHelper;

public class GraphActivity extends BaseActivity {

    public static final String GRAPH_ACTIVITY = "GRAPH_ACTIVITY";

    private Toolbar toolbar;
    private XYPlot plot;
    private AchievementBuilder achievementBuilder;
    private int finalPosition;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        initGUI();

        String title = achievementBuilder.getMotionCategory().name().replace("_", " ");
        initToolbarAndWindow(toolbar, title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        plotBuilder(new SimpleXYSeries(getAxis().getX(), getAxis().getY(), ""),
                    new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels));
    }

    @Override
    public void initGUI() {
        String positionString = getIntent().getStringExtra(ViewAchievementActivity.POSITION);
        toolbar = findViewById(R.id.toolbar);
        plot = findViewById(R.id.plot);
        finalPosition = Integer.parseInt(positionString);
        achievementBuilder = new DbPrHelper(this).getAllPr().get(finalPosition);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ViewAchievementActivity.class);
        intent.putExtra(GRAPH_ACTIVITY, finalPosition);
        startActivity(intent);
    }

    private void plotBuilder(XYSeries series, LineAndPointFormatter formatter){
        plot.addSeries(series, formatter);
        plot.getGraph().setPaddingLeft(30);
        plot.getGraph().setPaddingBottom(70);
        plot.getLegend().setVisible(false);
        plot.setDomainStep(StepMode.SUBDIVIDE, 4);

        plot.setRangeLabel("Result [" + achievementBuilder.getMotionCategory()
                                                          .toString()
                                                          .replace(" ", "") + "]");

        PanZoom.attach(plot);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM)
                .setFormat(new Format() {
                    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                    @Override
                    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                        long timestamp = ((Number) obj).longValue();
                        Date date = new Date(timestamp);
                        return dateFormat.format(date, toAppendTo, pos);
                    }
                    @Override
                    public Object parseObject(String source, ParsePosition pos) {
                        return null;
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private XYData getAxis(){
        List<PrRecordBuilder> list = achievementBuilder.getPrRecordBuilderList();
        Collections.sort(list, new CustomCoparators());

        List<Long> dateList = list.stream().filter(x -> !x.getResult().isEmpty()).map(y -> stringToDate(y.getDate()).getTime()).collect(Collectors.toList());
        List<Double> loadList = list.stream().map(PrRecordBuilder::getResult).filter(x -> !x.isEmpty())
                                                                             .map(Double::parseDouble).collect(Collectors.toList());
        return new XYData(dateList, loadList);
    }

    private Date stringToDate(String aDate) {
            if (aDate == null) return null;
            ParsePosition pos = new ParsePosition(0);
            SimpleDateFormat simpledateformat = new SimpleDateFormat("dd MMM yyyy");
            Date stringDate = simpledateformat.parse(aDate, pos);
            return stringDate;
    }
}

class XYData{
    private List<Long> xAxis;
    private List<Double> yAxis;

    XYData(List<Long> xAxis, List<Double> yAxis){
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    List<Double> getY() {
        return yAxis;
    }

    List<Long> getX() {
        return xAxis;
    }
}
