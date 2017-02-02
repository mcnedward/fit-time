package com.mcnedward.fittime.activities.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Edward on 2/1/2017.
 */

public class BaseFragment extends Fragment {

    public static final int MAIN = 0;
    public static final int CREATE = 1;

    public static BaseFragment newInstance(int fragmentCode) {
        switch (fragmentCode) {
            case MAIN:
                return new MainFragment();
            case CREATE:
                return new HistoryFragment();
            default:
                throw new IllegalArgumentException("That fragment does not exist!");
        }
    }

    public static FragmentCode[] fragments() {
        return new FragmentCode[] {
                new FragmentCode(MAIN, "Main"),
                new FragmentCode(CREATE, "History")
        };
    }

    public static class FragmentCode {
        private int mCode;
        private String mTitle;

        FragmentCode(int code, String title) {
            mCode = code;
            mTitle = title;
        }

        public int getCode() {
            return mCode;
        }

        public String getTitle() {
            return mTitle;
        }
    }

}
