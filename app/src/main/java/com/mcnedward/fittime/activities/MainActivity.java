package com.mcnedward.fittime.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.views.AddExerciseView;

public class MainActivity extends AppCompatActivity {

    private View mRootView;
    private MenuItem calendarItem;
    private MenuItem addItem;
    private AddExerciseView mAddExerciseView;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(mRootView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        calendarItem = menu.findItem(R.id.action_calendar);
        addItem = menu.findItem(R.id.action_add);
        calendarItem.setOnMenuItemClickListener(item -> true);
        addItem.setOnMenuItemClickListener(item -> {
            openPopup(getApplicationContext());
            return true;
        });
        return true;
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
        mPopupWindow.showAtLocation(mRootView, Gravity.CENTER, 0, 0);
    }

    private int getPopupWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (int) (dm.widthPixels * 0.8);
    }

}
