package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/*
* Notes
*
* dp r dip = density independent pixel: we get the same size regardless of screen density
*            One dp is always 1/160th of an inch on a device’s screen.
* sp = scale indepente pixel:  same as dp, and also take into account the user's font size preference
*
* marging vs padding
*
* margin attributes are layout parameters, which means is a call to widget's parent
* padding attibutes tells the widgoet how much bigger than its contents it should draw itself
*
*
* */

//Becuase we use the support library, this class inherate from fragmentactivity instead activity
public class CrimeActivity extends FragmentActivity {

    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        //The fragmentmanager call the lifecycle methods for every fragment

        //we call getsupportfragmentmana    ger because we use the suppor library
        FragmentManager fm = getSupportFragmentManager();
        //Get the fragment
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //Why ask for null?
            //because if the parent activity recreated, before it die, fragmentmanager saves the fragments it has.
            //so, the fragmentmanager could have a fragments to recreate everything as it was before.
        if(fragment == null)
        {
            fragment = new CrimeFragment();
            //first parameter: tell the fragment manager where is the fragment, and it uses this id like unique
                        //identifier  in  fragment manager.
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

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
