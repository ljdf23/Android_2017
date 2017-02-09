package com.bignerdranch.android.criminalintent;

import com.bignerdranch.android.criminalintent.Abstract.SingleFragmentActivity;

/**
 * Created by schizophrenia on 7/02/17.
 */

public class CrimeListActivity extends SingleFragmentActivity {


    @Override
    protected CrimeListFragment createFragment() {
        return new CrimeListFragment();
    }
}
