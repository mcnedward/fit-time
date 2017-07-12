package com.mcnedward.fittime.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mcnedward.fittime.R;

/**
 * Created by Edward on 2/5/2017.
 */

public class AddExerciseView extends LinearLayout {

    private Context mContext;
    private EditText mExerciseNameText;

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
        inflate(mContext, R.layout.view_add_exercise, this);
        setBackground(ContextCompat.getDrawable(context, R.drawable.border));

        mExerciseNameText = (EditText) findViewById(R.id.text_exercise_name);

        Button addExerciseButton = (Button) findViewById(R.id.button_add_exercise);
        addExerciseButton.setOnClickListener(v -> addExercise());
    }

    private void addExercise() {
        String name = mExerciseNameText.getText().toString();
        if ("".equals(name)) {
            Toast.makeText(mContext, "You need a name for your exercise!", Toast.LENGTH_SHORT).show();
            return;
        }
        mExerciseNameText.setText("");
    }
}
