package com.mcnedward.fittime.activities.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.exceptions.EntityAlreadyExistsException;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.repositories.ExerciseRepository;
import com.mcnedward.fittime.repositories.IExerciseRepository;
import com.mcnedward.fittime.views.AddExerciseView;
import com.mcnedward.fittime.views.ExerciseView;
import com.mcnedward.fittime.views.RepExerciseView;
import com.mcnedward.fittime.views.TimedExerciseView;

import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getName();

    private IExerciseRepository mExerciseRepository;
    private LinearLayout mExerciseContainer;
    private AddExerciseView mAddExerciseView;
    private PopupWindow mPopupWindow;
    private int mExerciseCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mExerciseRepository = new ExerciseRepository(view.getContext());
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        mExerciseContainer = (LinearLayout) view.findViewById(R.id.container_exercises);

        List<Exercise> exercises = mExerciseRepository.getAll();
        mExerciseCount = exercises.size();
        if (!exercises.isEmpty()) {

            for (Exercise exercise : exercises) {
                ExerciseView exerciseView = null;
                if (exercise.getType() == Exercise.TIMED) {
                    exerciseView = new TimedExerciseView(view.getContext(), exercise);
                } else if (exercise.getType() == Exercise.REP) {
                    exerciseView = new RepExerciseView(view.getContext(), exercise);
                }
                mExerciseContainer.addView(exerciseView);
            }
        }
        view.findViewById(R.id.button_popup).setOnClickListener(v -> openPopup(view.getContext()));
    }

    public void openPopup(Context context) {
        if (mAddExerciseView == null) {
            mAddExerciseView = new AddExerciseView(context);
        }
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mAddExerciseView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setElevation(5.0f);
            mPopupWindow.setWidth(getPopupWidth());
            mPopupWindow.setAnimationStyle(R.style.Animation);
        }
        mPopupWindow.showAtLocation(mExerciseContainer, Gravity.CENTER, 0, 0);
    }

    public void addExerciseToView(Exercise exercise) {
        try {
            mExerciseRepository.save(exercise);
        } catch (EntityAlreadyExistsException e) {
            Toast.makeText(getContext(), "That exercise is already added!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage(), e);
            return;
        }
        mExerciseContainer.removeView(mAddExerciseView);
        if (exercise.getType() == Exercise.TIMED) {
            mExerciseContainer.addView(new TimedExerciseView(getContext(), exercise));
        } else if (exercise.getType() == Exercise.REP) {
            mExerciseContainer.addView(new RepExerciseView(getContext(), exercise));
        }
        mExerciseCount++;
        if (mExerciseCount >= 5) return;
        mExerciseContainer.addView(mAddExerciseView);
    }

    private int getPopupWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) (dm.widthPixels * 0.8);
    }

}
