package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bignerdranch.android.criminalintent.Abstract.SingleFragmentActivity;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private String TAG = getClass().getSimpleName();
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";


    /**
     * Notice that the need for independence does not go both ways. CrimeActivity has to know plenty
     about CrimeFragment , including that it has a newInstance(UUID) method. This is fine. Hosting
     activities should know the specifics of how to host their fragments, but fragments should not have to
     know specifics about their activities. At least, not if you want to maintain the flexibility of independent
     fragments.
     * @return
     */
    @Override
    protected CrimeFragment createFragment(){
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {

        //this is the way to start an activity from a fragment or an activity.
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
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
