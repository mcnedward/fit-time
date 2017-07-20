package com.mcnedward.fittime.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.StepMode;
import com.mcnedward.fittime.R;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.History;
import com.mcnedward.fittime.models.WorkSet;
import com.mcnedward.fittime.repositories.exercise.ExerciseRepository;
import com.mcnedward.fittime.utils.Dates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 7/19/2017.
 */

public class ChartView extends LinearLayout {
    private static final String TAG = "ChartView";

    protected Context context;
    protected TextView txtNoDates;
    protected History history;
    protected XYPlot plot;
    protected SimpleXYSeries historySeries;
    protected List<Double> repAmounts;
    protected List<Integer> dates;
    protected double upperBound;
    protected int maxDomainStep = 10;
    protected String dateMessage;

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        history = new ExerciseRepository(context).getHistoryForCurrentDate();
        initialize();
    }

    private void initialize() {
        if (!isInEditMode()) {
            inflate(context, R.layout.view_chart, this);
            initializePlot();
        }
    }

    private void initializePlot() {
        plot = findViewById(R.id.plot_chart);
        txtNoDates = findViewById(R.id.txt_no_dates);
        plot.getLayoutManager().remove(plot.getLegend());
        plot.setBorderPaint(null);

        plot.setRangeLowerBoundary(0, BoundaryMode.AUTO);
        plot.getGraph().getRangeOriginLinePaint().setTextSize(20);
        plot.getGraph().getDomainOriginLinePaint().setTextSize(20);
        plot.setRangeStepValue(0);

        updatePlot();
    }

    protected void updatePlot() {
        refresh();

        double rangeIncrement = handleData();

        historySeries = new SimpleXYSeries(dates, repAmounts, null);
        plot.addSeries(historySeries, new LineAndPointFormatter(
                ContextCompat.getColor(context, R.color.LimeGreen),
                ContextCompat.getColor(context, R.color.ForestGreen),
                Color.TRANSPARENT, null));

        updatePlotWidget(rangeIncrement);

        if (dates.size() <= 1) {
            txtNoDates.setText(dateMessage);
            txtNoDates.setVisibility(VISIBLE);
        } else
            txtNoDates.setVisibility(GONE);

        plot.redraw();
    }

    private void updatePlotWidget(final double rangeIncrement) {
        if (history != null) {
            plot.setRangeStep(StepMode.INCREMENT_BY_VAL, rangeIncrement);
            //            plot.setRangeTopMin(upperBound + rangeIncrement);

            int goalCount = history.getExercises().size();
            plot.setDomainStep(StepMode.SUBDIVIDE, goalCount <= 10 ? goalCount : maxDomainStep);
            plot.setDomainStepValue(goalCount <= 10 ? goalCount : maxDomainStep);

            //            plot.setDomainValueFormat(new Format() {
            //                private SimpleDateFormat fromDateFormat = new SimpleDateFormat(Dates.NUMBER_DATE);
            //                private SimpleDateFormat toDateFormat = new SimpleDateFormat("dd/MM");
            //
            //                private int count = 0;
            //                private int fieldsShown = 0;
            //
            //                @Override
            //                public StringBuffer format(Object value, StringBuffer buffer, FieldPosition field) {
            //                    if (dates.size() == 0)
            //                        return buffer;
            //                    int position = count++;
            //                    int dateCount = dates.size();
            //                    if (dateCount > maxDomainStep) {
            //                        int fieldsToDisplay = dateCount / 2;
            //                        if (position % 2 == 0) {
            //                            int index = fieldsToDisplay * fieldsShown++;
            //                            if (index > dateCount - 1) index = dateCount - 1;
            //                            return handleBuffer(dates.get(index), buffer, field);
            //                        } else
            //                            return buffer;
            //                    }
            //                    return handleBuffer(dates.get(position), buffer, field);
            //                }
            //
            //                private StringBuffer handleBuffer(int dateValue, StringBuffer buffer, FieldPosition position) {
            //                    Date date = null;
            //                    try {
            //                        date = fromDateFormat.parse(String.valueOf(dateValue));
            //                    } catch (ParseException e) {
            //                        e.printStackTrace();
            //                    }
            //                    return toDateFormat.format(date, buffer, position);
            //                }
            //
            //                @Override
            //                public Object parseObject(String string, ParsePosition position) {
            //                    return null;
            //                }
            //            });
            //            plot.setRangeValueFormat(new Format() {
            //                private DecimalFormat format = new DecimalFormat("#.###");
            //
            //                @Override
            //                public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
            //                    return format.format(object, buffer, field);
            //                }
            //
            //                @Override
            //                public Object parseObject(String string, ParsePosition position) {
            //                    return null;
            //                }
            //            });
        }
    }

    private double handleData() {
        repAmounts = new ArrayList<>();
        dates = new ArrayList<>();
        upperBound = 0;
        double average = 0;
        double repCount = 0;

        List<Exercise> exercises = history.getExercises();

        for (Exercise exercise : exercises) {
            if (exercise.getType() == Exercise.REP) {
                List<WorkSet> workSetCount = exercise.getWorkSets();
                repCount += workSetCount.size();
                for (WorkSet workSet : workSetCount) {
                    double repAmount = Double.parseDouble(workSet.getValue());
                    average += repAmount;
                }
            }
        }

        repAmounts.add(repCount);
        dates.add(Dates.getDateAsNumber(history.getHistoryDate()));
        //            if (upperBound == 0 || upperBound < history.getStepAmount())
        //                upperBound = stepAmount;

        average /= repCount;
        return average;
    }

    public void notifyUpdateFinished() {
        plot.redraw();
    }

    public void editHistory() {
        updatePlot();
    }

    public void notifyDateChanged(int dateRange) {
        String dateRangeStamp = Dates.getDateFromRange(dateRange, Dates.PRETTY_DATE);
        String currentDateStamp = Dates.getCalendarPrettyDate();
        dateMessage = String.format("No activity found from %s to %s", dateRangeStamp, currentDateStamp);
    }

    public void refresh() {
        plot.clear();
    }
}
