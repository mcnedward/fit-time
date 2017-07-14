package com.mcnedward.fittime.views;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.utils.Extension;

/**
 * Created by Edward on 2/4/2017.
 */

public class RepExerciseView extends ExerciseView {
    private static final String TAG = RepExerciseView.class.getName();

    private EditText mRepCountText;

    public RepExerciseView(Context context, Exercise exercise) {
        super(context, exercise);
        initialize();
    }

    public RepExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        ImageView plus5Button = findViewById(R.id.button_plus_5);
        Extension.setRippleBackground(context, plus5Button);
        plus5Button.setOnClickListener(v -> updateValue(5));

        ImageView plusButton = findViewById(R.id.button_plus);
        Extension.setRippleBackground(context, plusButton);
        plusButton.setOnClickListener(v -> updateValue(1));

        ImageView remove5Button = findViewById(R.id.button_remove_5);
        Extension.setRippleBackground(context, remove5Button);
        remove5Button.setOnClickListener(v -> updateValue(-5));

        ImageView removeButton = findViewById(R.id.button_remove);
        Extension.setRippleBackground(context, removeButton);
        removeButton.setOnClickListener(v -> updateValue(-1));

        mRepCountText = findViewById(R.id.text_rep_count);
        mRepCountText.setSelection(mRepCountText.getText().length());
        mRepCountText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
                try {
                    int input = Integer.parseInt(dest.toString() + source.toString());
                    if (input < 0) return "0";
                    return String.valueOf(input);
                } catch (NumberFormatException e) {
                    Log.w(TAG, "There was a problem formatting the input for the rep counter...", e);
                    return "0";
                }
            }
        }});

        ImageView mCheckButton = findViewById(R.id.button_check_rep);
        Extension.setRippleBackground(context, mCheckButton);
        mCheckButton.setOnClickListener(v -> finishRep());
    }

    private void updateValue(int amount) {
        int currentAmount = Extension.tryGetInt(mRepCountText.getText().toString());
        currentAmount += amount;
        mRepCountText.setText(String.valueOf(currentAmount));
        mRepCountText.setSelection(mRepCountText.getText().length());
    }

    private void finishRep() {
        int amount = Extension.tryGetInt(mRepCountText.getText().toString());
        if (amount == 0) {
            Toast.makeText(context, "You need to enter a value above 0 for your reps!", Toast.LENGTH_SHORT).show();
            return;
        }
        addSet(mRepCountText.getText().toString());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.item_rep_exercise;
    }
}
