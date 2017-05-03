package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    @BindView(R.id.crime_hour) Button mTimeButton;


    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;

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
        getActivity().setResult(Activity.RESULT_OK);
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQUEST_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            mCrime.setDate(date);
            updateDate();
        }

        if(requestCode == REQUEST_TIME)
        {
            Date date = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);


            mCrime.setDate(date);
            updateDate();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDate() {
        mDateButton.setText(new SimpleDateFormat("EEE, d MMM yyyy hh:mm").format(mCrime.getDate()));
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

        //this code es a simple implementation for retrieve parameters, reading from the intent of the activity, it is a simple implementation
        //but but it means that CrimeFragment, as currently written, cannot be used with just any activity.

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

        updateDate();

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        // mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());


                /**
                 *
                 * With activities, you call startActivityForResult(…), and the ActivityManager keeps track of the
                 parent-child activity relationship. When the child activity dies, the ActivityManager knows which
                 activity should receive the result.

                 You can create a similar connection by making CrimeFragment the target fragment of
                 DatePickerFragment.

                 This connection is automatically reestablished after both CrimeFragment and
                 DatePickerFragment are destroyed and re-created by the OS.
                 *
                 */
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox)view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        return view;
    }

}
