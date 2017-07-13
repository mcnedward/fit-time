package com.mcnedward.fittime.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.utils.Receiver;
import com.mcnedward.fittime.views.AddExerciseView;
import com.mcnedward.fittime.views.MainView;

public class MainActivity extends AppCompatActivity {

    private View mRootView;
    private MainView mMainView;
    private AddExerciseView mAddExerciseView;
    private PopupWindow mPopupWindow;
    private AddExerciseReceiver mReceiver;
    private int mPopupWidth;
    private int mPopupYOffset;
    private boolean mIsReceiverRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(mRootView);
        mMainView = (MainView) findViewById(R.id.view_main);
        mReceiver = new AddExerciseReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem calendarItem = menu.findItem(R.id.action_calendar);
        MenuItem addItem = menu.findItem(R.id.action_add);
        calendarItem.setOnMenuItemClickListener(item -> true);
        addItem.setOnMenuItemClickListener(item -> {
            openPopup(getApplicationContext());
            return true;
        });
        return true;
    }

    @Override
    public void onResume() {
        if (!mIsReceiverRegistered) {
            mIsReceiverRegistered = true;
            registerReceiver(mReceiver, new IntentFilter(Receiver.ADD_EXERCISE_ACTION));
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mIsReceiverRegistered) {
            mIsReceiverRegistered = false;
            unregisterReceiver(mReceiver);
        }
        super.onPause();
    }

    public void openPopup(Context context) {
        if (mAddExerciseView == null) {
            mAddExerciseView = new AddExerciseView(context);
        }
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mAddExerciseView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setElevation(5.0f);
            mPopupWindow.setAnimationStyle(R.style.Animation);
            initializePopupDimensions();
            mPopupWindow.setWidth(mPopupWidth);

            mAddExerciseView.setPopupWindow(mPopupWindow);

        }
        mPopupWindow.showAtLocation(mRootView, Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, mPopupYOffset);
        mAddExerciseView.focusEditText();
    }

    private void initializePopupDimensions() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mPopupWidth = (int) (dm.widthPixels * 0.8);
        mPopupYOffset = (int) (dm.heightPixels * 0.2);
    }

    private final class AddExerciseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Receiver.ADD_EXERCISE_ACTION)) {
                Exercise exercise = (Exercise) intent.getSerializableExtra(Receiver.EXERCISE);
                mMainView.updateExercises(exercise);
            }
        }
    }
}
