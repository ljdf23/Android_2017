package com.bignerdranch.android.criminalintent.Abstract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.bignerdranch.android.criminalintent.CrimeListFragment;
import com.bignerdranch.android.criminalintent.R;

/**
 * Created by schizophrenia on 7/02/17.
 */


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

public abstract class SingleFragmentActivity extends FragmentActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //The fragmentmanager call the lifecycle methods for every fragment
        //we call getsupportfragmentmanager because we use the suppor library
        FragmentManager fm= getSupportFragmentManager();
        //Get the fragment
        Fragment fragment  = fm.findFragmentById(R.id.fragment_container);
        //Why ask for null?
        //because if the parent activity recreated, before it die, fragmentmanager saves the fragments it has.
        //so, the fragmentmanager could have a fragments to recreate everything as it was before.
        if(fragment == null)
        {
            fragment = createFragment();
            //first parameter: tell the fragment manager where is the fragment, and it uses this id like unique
            //identifier  in  fragment manager.
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
