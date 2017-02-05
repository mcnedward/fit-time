package com.mcnedward.fittime.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.activities.fragment.MainFragment;
import com.mcnedward.fittime.models.Exercise;

/**
 * Created by Edward on 2/5/2017.
 */

public class AddExerciseView extends LinearLayout {

    private Context mContext;
    private MainFragment mParent;
    private EditText mExerciseNameText;
    private RadioButton mTimedButton;
    private RadioButton mRepButton;

    public AddExerciseView(Context context, MainFragment parent) {
        super(context);
        mParent = parent;
        initialize(context);
    }

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
        inflate(mContext, R.layout.item_add_exercise, this);

        mExerciseNameText = (EditText) findViewById(R.id.text_exercise_name);
        mTimedButton = (RadioButton) findViewById(R.id.radio_timed);
        mRepButton = (RadioButton) findViewById(R.id.radio_rep);

        Button addExerciseButton = (Button) findViewById(R.id.button_add_exercise);
        addExerciseButton.setOnClickListener(v -> addExercise());
    }

    private void addExercise() {
        String name = mExerciseNameText.getText().toString();
        int type = mTimedButton.isChecked() ? Exercise.TIMED : Exercise.REP;
        if ("".equals(name)) {
            Toast.makeText(mContext, "You need a name for your exercise!", Toast.LENGTH_SHORT).show();
            return;
        }
        mExerciseNameText.setText("");
        mParent.addExerciseToView(new Exercise(name, type));
    }
}
