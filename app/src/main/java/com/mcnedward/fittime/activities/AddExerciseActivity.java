package com.mcnedward.fittime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.mcnedward.fittime.R;

/**
 * Created by Edward on 2/1/2017.
 */

public class AddExerciseActivity extends AppCompatActivity {
    private static final String TAG = AddExerciseActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        initialize();
    }

    private void initialize() {
        String[] types = new String[] {
                "Timed",
                "Repetitive"
        };
        Spinner spinner = (Spinner) findViewById(R.id.exercise_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_row, types);
        spinner.setAdapter(adapter);
    }

}
