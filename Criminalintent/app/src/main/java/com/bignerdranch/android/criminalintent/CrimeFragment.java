package com.bignerdranch.android.criminalintent;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.bignerdranch.android.criminalintent.Model.Crime;
import com.bignerdranch.android.criminalintent.Model.CrimeLab;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Luis on 23/01/2017.
 */

public class CrimeFragment  extends Fragment {

    @BindView(R.id.crime_title) TextView mTitleField;
    @BindView(R.id.crime_date)  Button mDateButton;
    @BindView(R.id.crime_solved)   CheckBox mSolvedCheckBox;


    private static final String ARG_CRIME_ID = "crime_id";
    private Unbinder unbinder;
    private Crime mCrime;

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCrime = new Crime();

        /**
         * with this code:
         *
         * CrimeFragment is no longer a reusable
         * building block because it expects that it will always be hosted by an activity whose Intent defines an
         * extra named com.bignerdranch.android.criminalintent.crime_id
         */

         // UUID crimeId = (UUID) getActivity()
         //        .getIntent() //The getIntent() method returns the Intent that was used to start CrimeActivity
         //       .getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
         //mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);


        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_crime,container,false);
        unbinder = ButterKnife.bind(this, view);

        mTitleField.setText(mCrime.getTitle());
        mSolvedCheckBox.setChecked(mCrime.isSolved());

        //mTitleField = (EditText)view.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //mDateButton = (Button)view.findViewById(R.id.crime_date);
       // mDateButton.setText(new SimpleDateFormat("EEE, d MMM yyyy").format(mCrime.getDate()));

        mDateButton.setEnabled(false);

        //mSolvedCheckBox = (CheckBox)view.findViewById(R.id.crime_solved);
        //mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        //    @Override
        //    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        //        mCrime.setSolved(b);
        //    }
        //});

        return view;
    }
}
