package com.bignerdranch.android.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.Abstract.SingleFragmentActivity;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Gerardo on 10/05/2017.
 */

public class DatePickerActivity extends SingleFragmentActivity {

    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";


    @Override
    protected DatePickerFragment createFragment() {

        Date date = (Date) getIntent().getSerializableExtra(EXTRA_DATE);
        return DatePickerFragment.newInstance(date);
    }

    public static Intent newIntent(Context packageContext, Date crime)
    {
        Intent intent = new Intent(packageContext, DatePickerActivity.class);
        intent.putExtra(EXTRA_DATE, crime);
        return intent;
    }
}
