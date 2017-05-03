package com.bignerdranch.android.criminalintent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.Model.Crime;
import com.bignerdranch.android.criminalintent.Model.CrimeLab;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;

/**
 * Created by Luis on 26/04/2017.
 */

public class CrimePagerActivity extends FragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintente.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.getInstance(this).getCrimes();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();


        /**
         * FragmentStatePagerAdapter is your agent managing the conversation with ViewPager. For your agent
         * to do its job with the fragments that getItem(int) returns, it needs to be able to add them to your
         * activity. That is why it needs your FragmentManager.
         *
         * FragmentStatePagerAdapter is adding the fragments you return to your
         * activity and helping ViewPager identify the fragments’ views so that they can be placed correctly
         */

        /**
         *
         *  FragmentStatePagerAdapter - It unloads your fragments when they are no longer needed
         *  FragmentPagerAdapter  - When your fragment is no longer needed, FragmentPagerAdapter calls detach(Fragment) on the transaction, instead of remove(Fragment).
                                    This destroys the fragment’s view, but leaves the fragment instance alive in the FragmentManager. So the fragments created by
                                    FragmentPagerAdapter are never destroyed
         *
         */
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(Context packageContext, UUID crimeId)
    {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
