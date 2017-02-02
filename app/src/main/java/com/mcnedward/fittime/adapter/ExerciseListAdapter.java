package com.mcnedward.fittime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.views.ExerciseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {
    private static final String TAG = ExerciseListAdapter.class.getName();

    private List<Exercise> groups;
    protected Context context;
    protected LayoutInflater inflater;

    public ExerciseListAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public ExerciseListAdapter(Context context, List<Exercise> groups) {
        super(context, R.layout.item_timed_exercise);
        this.context = context;
        this.groups = groups;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new ExerciseView(context, getItem(position));
        }
        ExerciseView view = (ExerciseView) convertView;
        view.update(this, groups.get(position));
        return view;
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
        return groups.size();
    }

    @Override
    public Exercise getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setGroups(List<Exercise> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    public void reset() {
        groups = new ArrayList<>();
    }
}
