package com.mcnedward.fittime.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.fragments.HistoryFragment;
import com.mcnedward.fittime.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.container_fragment) != null) {
            if (savedInstanceState != null) return;

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_fragment, new MainFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem calendarItem = menu.findItem(R.id.action_calendar);
        calendarItem.setOnMenuItemClickListener(item -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_fragment, new HistoryFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        });
        return true;
    }
}
