package com.mcnedward.fittime.activities.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.activities.MainActivity;
import com.mcnedward.fittime.adapter.ExerciseListAdapter;
import com.mcnedward.fittime.exceptions.EntityDoesNotExistException;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.History;
import com.mcnedward.fittime.models.WorkSet;
import com.mcnedward.fittime.repositories.exercise.ExerciseRepository;
import com.mcnedward.fittime.repositories.workSet.WorkSetRepository;
import com.mcnedward.fittime.utils.Dates;
import com.mcnedward.fittime.utils.Extension;
import com.mcnedward.fittime.utils.Receiver;
import com.mcnedward.fittime.views.AddExerciseView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Edward on 7/13/2017.
 */

public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getName();

    private ExerciseListAdapter mListAdapter;
    private ExerciseRepository mExerciseRepository;
    private WorkSetRepository mWorkSetRepository;
    private RelativeLayout mMainContainer;
    private AddExerciseView mAddExerciseView;
    private PopupWindow mPopupWindow;
    private AddExerciseReceiver mReceiver;
    private boolean mIsReceiverRegistered = false;
    private int mPopupWidth;
    private int mPopupYOffset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mExerciseRepository = new ExerciseRepository(getContext());
        mWorkSetRepository = new WorkSetRepository(getContext());
        mReceiver = new AddExerciseReceiver();
        initialize(view, inflater, container);
        return view;
    }

    private void initialize(View view, LayoutInflater inflater, ViewGroup container) {
        mMainContainer = view.findViewById(R.id.main_container);

        ListView listView = view.findViewById(R.id.list_exercises);
        Button logExercisesButton = view.findViewById(R.id.button_log_history);

        List<Exercise> exercises = mExerciseRepository.getAll();
        mListAdapter = new ExerciseListAdapter(view.getContext(), exercises);
        listView.setAdapter(mListAdapter);

        // Add the exercise placeholder view to the footer of the list
        View exercisePlaceholderView = inflater.inflate(R.layout.item_exercise_placeholder, container);
        listView.addFooterView(exercisePlaceholderView);
        exercisePlaceholderView.setOnClickListener(v -> openPopup(getContext()));

        logExercisesButton.setOnClickListener(v -> logExercises());
        logExercisesButton.setOnLongClickListener(v -> {
            getHistory(getContext());
            return true;
        });
    }

    private void logExercises() {
        String historyDate = Dates.getDatabaseDateStamp();
        for (Exercise exercise : mListAdapter.getExercises()) {
            // Use the current date for each work set's history, then update
            for (WorkSet workSet : exercise.getWorkSets()) {
                workSet.setWorkDate(historyDate);
                try {
                    mWorkSetRepository.update(workSet);
                } catch (EntityDoesNotExistException e) {
                    e.printStackTrace();
                    // TODO Handle this?
                }
            }
            // Clear out the work sets for the day
            exercise.clearWorkSets();
        }
    }

    public void getHistory(Context context) {
        History history = mExerciseRepository.getHistoryForCurrentDate();
        List<String> eList = new ArrayList<>();
        for (Exercise exercise : history.getExercises()) {
            List<String> wList = exercise.getWorkSets().stream().map(WorkSet::toString).collect(Collectors.toList());
            String e = exercise.getName() + " Sets: " + Extension.join(wList, ", ");
            eList.add(e);
        }
        Toast.makeText(context, "Exercises: " + Extension.join(eList, "\n"), Toast.LENGTH_LONG).show();
    }

    public void openPopup(Context context) {
        if (mAddExerciseView == null) {
            mAddExerciseView = new AddExerciseView(context);
        }
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mAddExerciseView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setElevation(5.0f);
            mPopupWindow.setAnimationStyle(R.style.Animation);
            initializePopupDimensions(context);
            mPopupWindow.setWidth(mPopupWidth);

            mAddExerciseView.setPopupWindow(mPopupWindow);
        }
        mPopupWindow.showAtLocation(mMainContainer, Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, mPopupYOffset);
        mAddExerciseView.focusEditText();
    }

    private void initializePopupDimensions(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mPopupWidth = (int) (dm.widthPixels * 0.8);
        mPopupYOffset = (int) (dm.heightPixels * 0.2);
    }

    @Override
    public void onResume() {
        if (!mIsReceiverRegistered) {
            mIsReceiverRegistered = true;
            getActivity(). registerReceiver(mReceiver, new IntentFilter(Receiver.ADD_EXERCISE_ACTION));
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mIsReceiverRegistered) {
            mIsReceiverRegistered = false;
            getActivity().unregisterReceiver(mReceiver);
        }
        super.onPause();
    }

    private final class AddExerciseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Receiver.ADD_EXERCISE_ACTION)) {
                Exercise exercise = (Exercise) intent.getSerializableExtra(Receiver.EXERCISE);
                mListAdapter.addExercise(exercise);
            }
        }
    }

}
