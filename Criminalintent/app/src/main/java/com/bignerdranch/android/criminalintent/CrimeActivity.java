package com.bignerdranch.android.criminalintent;

import android.util.Log;

import com.bignerdranch.android.criminalintent.Abstract.SingleFragmentActivity;

public class CrimeActivity extends SingleFragmentActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected CrimeFragment createFragment() {
        return new CrimeFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


}
