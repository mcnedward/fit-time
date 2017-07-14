package com.mcnedward.fittime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.views.RepExerciseView;
import com.mcnedward.fittime.views.TimedExerciseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class ExerciseListAdapter extends BaseAdapter {
    private static final String TAG = ExerciseListAdapter.class.getName();

    private List<Exercise> mExercises;
    protected Context context;
    protected LayoutInflater inflater;

    public ExerciseListAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public ExerciseListAdapter(Context context, List<Exercise> exercises) {
        this.context = context;
        this.mExercises = exercises;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exercise exercise = mExercises.get(position);
        if (convertView == null) {
            if (exercise.getType() == Exercise.REP)
                convertView = new RepExerciseView(context, exercise);
            else if (exercise.getType() == Exercise.TIMED)
                convertView = new TimedExerciseView(context, exercise);
        }
//        ((ExerciseView)convertView).update(this, exercise);
        return convertView;
    }

    public void setExercises(List<Exercise> exercises) {
        this.mExercises = exercises;
        notifyDataSetChanged();
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void addExercise(Exercise exercise) {
        this.mExercises.add(exercise);
        notifyDataSetChanged();
    }

    public void reset() {
        mExercises = new ArrayList<>();
    }

//    public void notifyDataSetChanged(boolean triggerReload) {
//        if (triggerReload) {
//            List<Exercise> data = loader.loadInBackground();
//            setGroups(data);
//        } else
//            notifyDataSetChanged();
//    }
//
//    public void setLoader(ExerciseDataLoader loader) {
//        this.loader = loader;
//    }

    @Override
    public int getCount() {
        return mExercises.size();
    }

    @Override
    public Exercise getItem(int position) {
        return mExercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
