package com.mcnedward.fittime.views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.exceptions.EntityAlreadyExistsException;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.repositories.ExerciseRepository;
import com.mcnedward.fittime.repositories.IExerciseRepository;
import com.mcnedward.fittime.utils.Receiver;

/**
 * Created by Edward on 2/5/2017.
 */

public class AddExerciseView extends LinearLayout {

    private Context mContext;
    private EditText mExerciseNameText;
    private RadioButton mTimedButton;
    private PopupWindow mPopupWindow;
    private IExerciseRepository mExerciseRepository;

    public AddExerciseView(Context context) {
        super(context);
        initialize(context);
    }

    public AddExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        this.mContext = context;
        mExerciseRepository = new ExerciseRepository(context);

        inflate(mContext, R.layout.view_add_exercise, this);
        setBackground(ContextCompat.getDrawable(context, R.drawable.border));

        mExerciseNameText = (EditText) findViewById(R.id.text_exercise_name);
        mTimedButton = (RadioButton) findViewById(R.id.radio_timed);

        findViewById(R.id.button_add_exercise).setOnClickListener(v -> addExercise());
    }

    private void addExercise() {
        String name = mExerciseNameText.getText().toString();
        int type = mTimedButton.isChecked() ? Exercise.TIMED : Exercise.REP;
        if ("".equals(name)) {
            Toast.makeText(mContext, "You need a name for your exercise!", Toast.LENGTH_SHORT).show();
            return;
        }
        mExerciseNameText.setText("");

        Exercise exercise = new Exercise(name, type);
        try {
            mExerciseRepository.save(exercise);
        } catch (EntityAlreadyExistsException e) {
            mPopupWindow.dismiss();
            return;
        }

        Intent intent = new Intent(Receiver.ADD_EXERCISE_ACTION);
        intent.putExtra(Receiver.EXERCISE, exercise);
        mContext.sendBroadcast(intent);

        mPopupWindow.dismiss();
    }

    public void focusEditText() {
        mExerciseNameText.requestFocus();
        mExerciseNameText.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mExerciseNameText, InputMethodManager.SHOW_IMPLICIT);
        }, 200);
    }

    public void setPopupWindow(PopupWindow popupWindow) {
        mPopupWindow = popupWindow;
    }
}
